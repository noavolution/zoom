import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;

public class FileStructure
{
    public static String src;
    public static HashMap<String, String> subFolders = new HashMap<String, String>();
    public static HashMap<String, String> installationStructure = new HashMap<String, String>();


    public static void init()
    {
        src = System.getProperty("user.home") + "/zoom/data";
        subFolders.put("engines", "engines");
        subFolders.put("installations", "installations");
        subFolders.put("maps", "maps");
        subFolders.put("iWADs", "iWADs");

        installationStructure.put("saves", "saves");
        installationStructure.put("config", "installation.config");
        installationStructure.put("engine", "engine");
    }

    public static boolean createMainFileStructure()
    {
        init();
        try
        {
            File data = new File(src);
            data.mkdirs();

            for(String folder : subFolders.values() )
            {
                File subFolder = new File(src + "/" + folder);
                if(!subFolder.exists())
                {
                    if(!subFolder.mkdirs()) {System.out.println("not created");}
                }
            }

            return true;
        }
        catch(Exception createStructureExcept)
        {
            throw new RuntimeException(createStructureExcept);
        }
    }

    public static boolean createInstallation(String installationName, String engineName, boolean separateEngine, boolean customEngine, String description)
    {
        init();
        try
        {
            System.out.println(src + "/" + subFolders.get("installations") + "/" + installationName);

            File installation = new File(src + "/" + subFolders.get("installations") + "/" + installationName);
            File saves = new File(src + "/" + subFolders.get("installations") + "/" + installationName + "/" + installationStructure.get("saves"));
            File config = new File(src + "/" + subFolders.get("installations") + "/" + installationName + "/" + installationStructure.get("config"));
            File engine = new File(src + "/" + subFolders.get("installations") + "/" + installationName + "/" + installationStructure.get("engine"));
            System.out.println(config.toString());
            if(!installation.exists())
            {
                installation.mkdirs();
                saves.mkdirs();
                engine.mkdirs();
                config.createNewFile();
                Properties prop = new Properties();
                FileOutputStream fos = new FileOutputStream(config);

                prop.setProperty("inst.name", installationName);
                prop.setProperty("inst.engine", engineName);
                prop.setProperty("inst.separateEngine", String.valueOf(separateEngine));
                prop.setProperty("inst.customEngine", String.valueOf(customEngine));
                prop.setProperty("inst.description", description);
                prop.store(fos, null);

                fos.close();

                return true;
            }
            else
            {
                MessageBox.showInfo("Installation already exists", "Info");

                return false;
            }

        }
        catch(Exception createInstallationExcept)
        {
            throw new RuntimeException(createInstallationExcept);
        }
    }

    public static boolean deleteInstallation(String installationName)
    {
        init();
        try
        {
            System.out.println(src + "/" + subFolders.get("installations") + "/" + installationName);
            File installation = new File(src + "/" + subFolders.get("installations") + "/" + installationName);

            if(installation.exists())
            {
                File[] contents = installation.listFiles();
                if(contents != null)
                {
                    for(File f : contents)
                    {
                        f.delete();
                    }
                }
                installation.delete();
            }
            else
            {
                MessageBox.showInfo("Installation does not exist", "Info");
            }


            return true;
        }
        catch(Exception createInstallationExcept)
        {
            throw new RuntimeException(createInstallationExcept);
        }
    }

    public static DefaultListModel listInstallations()
    {
        init();
        DefaultListModel list = new DefaultListModel();
        File installations = new File(src + "/" + subFolders.get("installations"));
        try
        {
            if(installations.exists())
            {
                File[] installation = installations.listFiles();
                for(File f : installation)
                {
                    if(f.isDirectory())
                    {
                        File config = new File(f + "/" + installationStructure.get("config"));
                        if(config.exists())
                        {
                            Properties prop = new Properties();
                            FileInputStream fis = new FileInputStream(config);
                            prop.load(fis);
                            list.addElement(prop.getProperty("inst.name"));
                        }
                    }
                }
            }
            else
            {
                FileStructure.createMainFileStructure();
            }

        }
        catch (Exception listInstallationsExcept)
        {
            throw new RuntimeException(listInstallationsExcept);
        }


        return list;
    }

    public static String getInstallationProp(String installationName, String propName)
    {
        init();
        File config = new File(src + "/" + subFolders.get("installations") + "/" + installationName + "/" + installationStructure.get("config"));
        try
        {
            if(config.exists() && config.isFile())
            {
                Properties prop = new Properties();
                FileInputStream fis = new FileInputStream(config);
                prop.load(fis);
                return prop.getProperty(propName);
            }
            else
            {
                return null;
            }
        }
        catch (Exception getInstallationPropsExcept)
        {
            throw new RuntimeException(getInstallationPropsExcept);
        }
    }

    public static File[] getIWADs()
    {
        File iWADs = new File(src + "/" + subFolders.get("iWADs"));
        if(iWADs.isDirectory())
        {
            File[] iWADarr = iWADs.listFiles();

            return iWADarr;
        }
        else
        {
            return null;
        }
    }

    public static File[] getEngines()
    {
        File engines = new File(src + "/" + subFolders.get("engines"));
        if(engines.isDirectory())
        {
            File[] enginesArr = engines.listFiles();

            return enginesArr;
        }
        else
        {
            return null;
        }
    }
}
