import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static String fokonyvtar = "";
    static List<String> duplaknelkul = new ArrayList<>();

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

        setFokonyvtar(args[0]);

        DirScan.Scan(eleres); //konyvtar rekurziv bejarasa

        System.out.println("Pathok: \n");


        for(String e : DirScan.pathok){
            System.out.println(e);
        }

        duplaknelkul = DeleteDuplicates(DirScan.pathok); // duplak eltuntetve
        System.out.println("Duplak nelkul");
        for(String e : duplaknelkul){
            System.out.println(e);
        }

        System.out.println("html-ek létrehozása: \n");

        int i = 0;
        for(String e : DirScan.pathok){

            System.out.println(">>"+e+" "+"sorszam: "+i+"<<");
            MakeHTML.makeHtml(e);
            i++;
       }



        System.out.println("\nMappak szama: "+DirScan.mappaszam);
    }//mainvege



    ///////////////////////////////////////////// Main metodusai ///////////////////////////////////////////////////////

    public static List<String> DeleteDuplicates(List<String> pathok) {
        List<String> r = new ArrayList<>();

        for (String e : pathok){
            if(DirScan.isEnd(e) == false && e.endsWith("/")) r.add(e);
            if(DirScan.isPic(e) == true) r.add(e);

        }

        return r;
    }// duplak torlesenek vege


    public static void setFokonyvtar(String arg) {
        fokonyvtar = arg;
    }//fokonyvtar beallitasa


    public static boolean isDirectory(String FileName) {

        boolean result = false;

        if (FileName.contains("/") == true ) result = true;

        return result;

    }//isDir vege

}
