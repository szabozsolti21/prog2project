import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirScan {

    static int mappaszam = 0;

    static List<String> pathok= new ArrayList<>();

    public static void Scan(String args) {


        String eleres = args;

        //System.out.println("Vizsgalt diectory: "+eleres+"\n"); //itt irja ki az adott directory-t
        pathok.add(eleres);
        System.out.println("eleres hozzadva: "+eleres);
        File path = new File(eleres);

        String[] list;

        list = path.list();
        mappaszam++;
        //System.out.println("Tartalma: ");
        for(int i = 0; i < list.length; i++){
        //    System.out.println(list[i]); //itt irja ki a directory tartalmat
            String elereslisti= eleres+list[i];

                pathok.add(elereslisti);


            System.out.println("elereslisti hozzaadva: "+elereslisti);

        }

        //System.out.println("Tartalom vege\n");
        List<String> mappak = new ArrayList<>();

        for(int i = 0; i < list.length; i++){
            mappak.add(list[i]);

            String[] eleresiut = new String[1];
            eleresiut[0] = eleres;

            if(isEnd(list[i]) == false){
                eleresiut[0] += mappak.get(i)+"/";
                DirScan.Scan(eleresiut[0].toString());
            }

            eleresiut[0] = PathDeleteLast(eleresiut[0]);

        }

    }//scan vege

    ///////////////////////////////////////////// Scan vege, tovabbi metodusok /////////////////////////////////////////

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

        if (FileName.contains(".txt"))
            result = true;

        if (FileName.contains(".html"))
            result = true;

        return result;

    }//isEnd vege

    public static boolean isPic(String FileName) {

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

        return result;

    }//isPic vege

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

