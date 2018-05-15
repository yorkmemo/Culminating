package io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public abstract class Sys {
    public static String HOME = System.getProperty("user.home") + "/.dojo";

    private static String temp = null;

    static {
        try {
            temp = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static final String os = System.getProperty("os.name");

    public static final String name = temp;

    public static boolean isMac() {
        return os.toLowerCase().contains("mac");
    }

    public static boolean isWindows() {
        return os.toLowerCase().contains("win");
    }

    public static boolean hasInternet() {
        return true;
    }

    public static void check() {

        File home_folder = new File(HOME);

        if (!home_folder.exists() || !home_folder.isDirectory()) {
            if (!home_folder.mkdir()) {
                Output.error("System permissions read error");
            } else {
                boolean permissions = home_folder.setExecutable(true) &&  home_folder.setReadable(true);

                if (!permissions) {
                    Output.error("System permissions write error (code: 0.0)");
                } else if (Sys.isWindows()) {
                    try {
                        Files.setAttribute(home_folder.toPath(), "dos:hidden", true);
                    } catch (IOException e) {
                        Output.error("System permissions write error (code: 1.0)");
                    }
                }

            }
        }



        File lib_folder = new File(HOME + "/lib");

        if (!lib_folder.exists() || !lib_folder.isDirectory()) {
            if (!lib_folder.mkdir()) {
                Output.error("System permissions read error for library");
            } else {
                boolean permissions = lib_folder.setExecutable(true) && lib_folder.setReadable(true);

                if (!permissions) {
                    Output.error("System permissions write error (code: 5.0)");
                }

            }
        }

        if (lib_folder.exists()) {
            File f = new File("jar:io.jar");
            try {
                File folder =  new File(Sys.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

                if (folder.isFile()) { //run from jar file
                    folder = folder.getParentFile();
                }

                File io_file =  new File(folder.getPath() + "/io.jar");
                File destination = new File(HOME + "/lib/io.jar");
                System.out.println(io_file.getPath());
              //  System.out.println(new File(Sys.class.getResource("../io.jar").toExternalForm()).toPath().getParent().toString());
                Files.copy(io_file.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                boolean permissions = destination.setExecutable(true) && destination.setReadable(true) && destination.setWritable(true);
            } catch (IOException e) {
                Output.error("File system library error");
            } catch (URISyntaxException e) {
                Output.error("File system syntax error");
            }


            //Sys.copyFile( Sys.class.getResource("io.jar").getPath(), Sys.HOME + "/lib");
        }


        if (isWindows() && home_folder.exists() && home_folder.isDirectory()) {
            File say = new File(HOME + "/say.vbs");

            if (!say.exists() || !say.isFile()) {
                List<String> lines = Arrays.asList(
                        "Dim Speak",
                        "Set Speak=CreateObject(\"sapi.spvoice\")",
                        "",
                        "ReDim arr(WScript.Arguments.Count-1)",
                        "For i = 0 To WScript.Arguments.Count-1",
                        "   arr(i) = WScript.Arguments(i)",
                        "Next",
                        "",
                        "rem WScript.Echo Speak.GetVoices.Count",
                        "Speak.Speak Join(arr)",
                        ""
                );

                try {
                    Files.write(say.toPath(), lines, Charset.forName("UTF-8"));

                    say = new File(HOME + "/say.vbs");

                    boolean permissions = say.setExecutable(true) && say.setReadable(true) && say.setWritable(true);

                    if (!permissions) {
                        Output.error("System permissions write error (code: 4.0)");
                    }
                } catch (IOException e) {
                    Output.error("System permissions write error (code: 3.0)");
                }
            }
        }

        Data.initialize();
        User.initialize();
    }


    public static void writeFile(String filename, String content) {
        File file = new File(filename);

        if (file.exists()) {
            file.delete();
        }

        try {
            Files.write(file.toPath(), content.getBytes());
        } catch (IOException e) {
            Output.error("Cannot write to file" + (filename.length() < HOME.length() || !filename.substring(0, HOME.length()).equalsIgnoreCase(HOME) ? ": " + filename : ""));
        }
    }


    public static void makeDir(String folder) {
        File file = new File(folder);

        if (file.exists() && file.isFile()) {
            file.delete();
        }

        if (!file.exists()) {

            boolean permissions = file.mkdir() && file.setExecutable(true) && file.setReadable(true);

            if (!permissions) {
                Output.error("Cannot create folder: " + folder);
                return;
            }
        }
    }

    public static void copyFile(String source, String destination) {
        if (source.length() > 4 && source.substring(0,4).toLowerCase().equals("jar:")) {
            InputStream link = Sys.class.getResourceAsStream("../"+source.substring(4));

            String filename = source.substring(4);
            File dest = new File(destination + "/" + new File(filename).getName());

            try {
                Files.copy(link, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

                boolean permissions = dest.setExecutable(true) && dest.setReadable(true) && dest.setWritable(true);

            } catch (IOException e) {
                Output.error("Cannot copy file: " + source.substring(4));
            }
        } else {

            try {
                Files.copy(new File(source).toPath(), new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Output.error("Cannot copy file: " + source);
            }

        }
    }

}
