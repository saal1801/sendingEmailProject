package main.java.com.email.webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;




public class HTMLReader {

    public static final String INBOX_PAGE = readFile("EmailPage.html");

    //public static final HTMLReader INSTANCE = new HTMLReader();



    public String getFromTo(String from, String to, String html) {
        if (html == null) {
            return null;
        }
        int start = html.indexOf(from);
        if (start < 0) {
            return null;
        }
        int stop = to != null ? html.indexOf(to, start + from.length()) : -1;
        if (stop < 0) {
            return html.substring(start);
        }
        return html.substring(start, stop);
    }

    public String getFromToContent(String from, String to, String html) {
        if (html == null) {
            return null;
        }
        int start = html.indexOf(from);
        if (start < 0) {
            return null;
        }
        start += from.length();
        int stop = to != null ? html.indexOf(to, start) : -1;
        if (stop < 0) {
            return html.substring(start);
        }
        return html.substring(start, stop);
    }

    public Object[] getFromToPos(String from, String to, String html) {
        if (html == null) {
            return null;
        }
        int start = html.indexOf(from);
        if (start < 0) {
            return null;
        }
        int stop = to != null ? html.indexOf(to, start + from.length()) : -1;
        String content = null;
        if (stop < 0) {
            content = html.substring(start);
        }
        content = html.substring(start, stop + to.length());
        return new Object[] { content, Integer.valueOf(start), Integer.valueOf(stop) };
    }

    public String readHTML(String urlStr) throws IOException {
        StringBuffer buffer = new StringBuffer();
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            URLConnection con = url.openConnection();
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.8,sv;q=0.6");
            con.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");

            con.setReadTimeout(100000);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine + "\n");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public String getTagContent(String tag, String html) {
        int index = html.indexOf("<" + tag);
        if (index < 0) {
            return "";
        }

        index = html.indexOf(">", index + 1);
        int stop = html.indexOf("</" + tag + ">");
        String tmp = html.substring(index + 1, stop);
        tmp = tmp.replace("&nbsp", "");
        return tmp;
    }

    public String getTagContentWithTag(String tag, String html) {
        int index = html.indexOf("<" + tag);
        if (index < 0) {
            return null;
        }

        int stop = html.indexOf("</" + tag + ">");
        return html.substring(index, stop) + "</" + tag + ">";
    }

    public String cropFromLast(String string, String html) {
        int index = html.lastIndexOf(string);
        if (index < 0) {
            return html;
        }
        return html.substring(index + 1);
    }

    public String removeTag(String tag, String html) {
        int index = html.indexOf("<" + tag);

        if (index < 0) {
            html = html.replaceAll("</" + tag + ">", "");

            return html;
        }
        int stop = html.indexOf(">", index);
        String tagStart = html.substring(index, stop + 1);
        String tmp = html.substring(0, index) + html.substring(stop + 1);

        return removeTag(tag, tmp);
    }

    public String cropTo(String string, String html) {
        int index = html.indexOf(string);
        if (index < 0) {
            return html;
        }
        return html.substring(0, index);
    }

    public String getTDContent(String html) {
        String tmp = html;
        tmp = removeTag("span", tmp);
        tmp = removeTag("a", tmp);
        tmp = tmp.replaceAll("&nbsp;", "");
        tmp = cropTo("</td>", tmp);
        tmp = cropFromLast(">", tmp);
        return tmp;
    }

    public List<String> getTags(String tag, String html) {
        String tmp = new String(html);
        List<String> list = new ArrayList();
        String tagContent = getTagContentWithTag(tag, tmp);
        if (tagContent == null) {
            return list;
        }
        if (tagContent != null) {
            list.add(tagContent);
            tmp = tmp.replace(tagContent, "");
            list.addAll(getTags(tag, tmp));
        }
        return list;
    }

    public static String removeSpecialChars(String playerStr) {
        String result = new String(playerStr);
        result = result.replace("ö", "o");
        result = result.replace("Ö", "O");
        result = result.replace("Ø", "O");
        result = result.replace("ø", "o");

        result = result.replace("á", "a");
        result = result.replace("Á", "A");

        result = result.replace("à", "a");
        result = result.replace("À", "A");

        result = result.replace("å", "a");
        result = result.replace("Å", "A");

        result = result.replace("ä", "a");
        result = result.replace("Ä", "A");

        result = result.replace("é", "e");
        result = result.replace("É", "E");

        result = result.replace("è", "e");
        result = result.replace("È", "E");

        result = result.replace("Ñ", "N");
        return result;
    }

    public static String fromHtml(String text) {
        if (text == null) {
            return "";
        }

        String result = text.replaceAll("<[^>]+>", "");
        result = result.replaceAll("\n", "");
        result = result.replaceAll("\r", "");
        result = result.replaceAll("&nbsp;", " ");
        result = result.replaceAll("&Ouml;", "Ö");
        result = result.replaceAll("&ouml;", "ö");
        result = result.replaceAll("&amp;", "&");

        result = result.replaceAll("&euml;", "ë");
        result = result.replaceAll("&eacute;", "é");
        result = result.replaceAll("&egrave;", "è");
        result = result.replaceAll("&uuml;", "ü");
        result = result.replaceAll("&oslash;", "ø");
        result = result.replaceAll("&ccedil;", "ç");
        result = result.replaceAll("&aelig;", "æ");
        result = result.replaceAll("&aring;", "å");
        result = result.replaceAll("&auml;", "ä");
        result = result.replaceAll("&ouml;", "ö");
        result = result.replaceAll("&acirc;", "â");
        result = result.replaceAll("&aacute;", "á");
        result = result.replaceAll("&agrave;", "à");
        result = result.replaceAll("&szlig;", "ß");

        result = result.replaceAll("&Euml;", "Ë");
        result = result.replaceAll("&Eacute;", "É");
        result = result.replaceAll("&Egrave;", "È");
        result = result.replaceAll("&Uuml;", "Ü");
        result = result.replaceAll("&Oslash;", "Ø");
        result = result.replaceAll("&Ccedil;", "Ç");
        result = result.replaceAll("&AElig;", "Æ");
        result = result.replaceAll("&Aring;", "Å");
        result = result.replaceAll("&Auml;", "Ä");
        result = result.replaceAll("&Ouml;", "Ö");
        result = result.replaceAll("&Acirc;", "Â");
        result = result.replaceAll("&Aacute;", "Á");
        result = result.replaceAll("&Agrave;", "À");

        result = result.trim();
        return result;
    }

    public static String readFile(String string) {
        URL url = new HTMLReader().getClass().getClassLoader().getResource(string);
        try {
            if (url != null) {
                return getStringFromInputStream(url.openStream());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert (classLoader != null);
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();

            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return (Class[]) classes.toArray(new Class[classes.size()]);
    }

    public static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert (!file.getName().contains("."));
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file

                        .getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
