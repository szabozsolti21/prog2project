import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class DeleteHTML{

    public static void main(String[] args) {

        if(args.length == 0){
            System.out.println("Nem adtál meg argumentumot!");
            System.exit(1);

        }

        String eleres = args[0];

        if(isDirectory(eleres) == false ){
            System.out.println("Nem könyvtárat adtál meg!");
            System.exit(1);

        }

        System.out.println(eleres); //itt irja ki az adott directory-t

        File path = new File(eleres);

        String[] list;

        list = path.list();

        for(int i = 0; i < list.length; i++){

            if(list[i].contains(".html"))System.out.println(list[i]); //itt irja ki a directory tartalmat

            if(list[i].contains(".html") == true){
                File d = new File(eleres+list[i]);

                if(d.delete())
                {
                    System.out.println("File törölve");
                }
                else
                {
                    System.out.println("File törlése sikertelen");
                }
            }

        }//adott directory tartalma torolve

        List<String> mappak = new ArrayList<>();

        for(int i = 0; i < list.length; i++){
            mappak.add(list[i]);

            String eleresiut[] = new String[1];
            eleresiut[0] = eleres;

            if(isEnd(list[i]) == false){
                eleresiut[0] += mappak.get(i)+"/";
                DeleteHTML.main(eleresiut);
            }

            eleresiut[0] = PathDeleteLast(eleresiut[0]);

        }

    }//mainvege

    public static boolean isEnd(String FileName) {

        boolean result = false;
        if (FileName.contains(".jpeg"))
            result = true;

        if (FileName.contains(".jpg"))
            result = true;

        if (FileName.contains(".png"))
            result = true;

        if (FileName.contains(".gif"))
            result = true;

        if (FileName.contains(".jfif"))
            result = true;

        if (FileName.contains(".html"))
            result = true;

        return result;

    }//isPic vege

    public static boolean isDirectory(String FileName) {

        boolean result = false;

        if (FileName.contains("/") == true ) result = true;

        return result;

    }//isDir vege

    public static String PathDeleteLast(String EleresiUt) {

        String[] temp = EleresiUt.split("/");
        temp[temp.length - 1] = "";

        EleresiUt = "";

        for (int i = 0; i < temp.length - 1; i++) {
            EleresiUt = EleresiUt + temp[i] + "/";
        }

        return EleresiUt;

    }//pathDeleteLast vege
}



