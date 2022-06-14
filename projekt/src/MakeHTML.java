import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MakeHTML {

    public static void makeHtml(String path) {

        List<String> out = new ArrayList<>();

        String FileName = "";


        if (DirScan.isPic(path) == true) {

            FileName = path.replaceAll("jpg", "html"); //Kiirasi nev
            FileName = FileName.replaceAll("png", "html"); //Kiirasi nev

            //Foindex meghatarozasa
            String FoIndex = "";

            char[] patharray = path.toCharArray();
            int pontok = calculatePoints(Main.fokonyvtar);
            for(char e : patharray){
                if(e == '/') pontok ++;
            }

            if(pontok != 0){
                for (int i = 0 ; i < pontok ; i++ )

                FoIndex+="../";
            }
            FoIndex += "Index.html";

            //elozo es kovetkezo meghatarozasa
            String PrevPic = getPrevPic(path);
            String NextPic = getNextPic(path);

            //jelenlegi meghatarozasa
            String ThisPicName = path.replaceAll("/", "_");
            String[] array = ThisPicName.split("_");
            ThisPicName = array[array.length - 1];



            //Lista letrehozasa
            out.add("<!DOCTYPE html>\n" +
                    "<html lang=\"hu\" dir=\"ltr\">\n" +
                    "\t<head>\n" +
                    "\t\t<meta charset=\"utf-8\">\n" +
                    "\t\t<title>" + "Kép" + "</title>\n" +
                    "</head>\n" +
                    "\t<body>" +
                    "<h1><a href=\"" + FoIndex + "\">Start Page</a></h1>\n" +
                    "<hr>\n" +
                    "\t\t<a href=\"" + "Index.html" + "\">^^</a>\n" +
                    "\t\t<div class=\" XD \">\n" +
                    "\t\t\t<a href=\"" + PrevPic + "\"><<</a><h1 style=\"display:inline;\">" + ThisPicName + "</h1><a href=\"" + NextPic + "\">>></a>\n" +
                    "\t\t</div>\n" +
                    "\t\t<a href=\"" + NextPic + "\"><img src=\"" + ThisPicName + "\" alt=\" " + ThisPicName + "\" width=\"500\"height=\"auto\"}></a>\n" +
                    "\t</body>\n" +
                    "</html>");
            System.out.println("File irasa: "+FileName+"\n");
            fajliras(out, FileName);

        }//ha kep

        //------------------------------------- fentebb: ha kep, lentebb: ha index -------------------------------------

        if (DirScan.isEnd(path) == false && path.endsWith("/")) {



            String nev = path.replaceAll("/", "_");
            String[] array = nev.split("_");
            nev = array[array.length - 1];

            FileName += path + "Index.html";
            FileName = FileName.trim();


            List<String> Indexek = new ArrayList<>(); // ebben lesznek az almappak
//            //melyseg alapjan meghatarozzuk melyek tartoznak ide
//            //fomappa: 0 , ennek tartalma: 1 ...

            out.add("Ez egy index: "+nev);


            //Foindex meghatarozasa
            String FoIndex = "";

            char[] patharray = path.toCharArray();
            int pontok = calculatePoints(Main.fokonyvtar);;
            int melyseg = 6;
            for(char e : patharray){
                if(e == '/') pontok ++;
            }

            if(pontok == 0){
                melyseg = melyseg;
            }
            else{

                for (int i = 0 ; i < pontok ; i++ ){
                    FoIndex+="../";
                    melyseg++;
                }

            }
            FoIndex += "Index.html";

            //startpage fajlbairasa
            out.add("<!DOCTYPE html>\n" +
                    "<html lang=\"hu\" dir=\"ltr\">\n" +
                    "\t<head>\n" +
                    "\t\t<meta charset=\"utf-8\">\n" +
                    "\t\t<title>" + "Index" + "</title>\n" +
                    "\t</head>\n" +
                    "\t<body>\n" +
                    "\t\t<h1><a href=\"" + FoIndex + "\">Start Page</a></h1>\n" +
                    "\t\t<hr>\n" +
                    "\t\t<h1>Directories:</h1>\n");

            //visszanyil faljbairasa
            if (path.equals(Main.fokonyvtar) == true)
                out.add("<pre><a href=" + "Index.html" + ">&#9^^</a></pre>");
            else {
                out.add("<pre><a href=" + "../Index.html" + ">&#9^^</a></pre>");
            }

            //Mappak fajlbairasa
            Indexek = getSubFolders(path);

            for(String mappa : Indexek){
                String mappanev = getFilename(mappa);
                String mappaindex= mappanev+"/Index.html";
                out.add("\t\t<ul>\n" +
                            "\t\t\t<li><a href=\"" + mappaindex + "\">" + mappanev + "</a></li>\n" +
                            "\t\t</ul>\n");
            }


            //Kepek fajbairasa

            out.add("<hr>\n" +
                    "\t\t<h1>Images:</h1>\n" +
                    "\t\t<ul>");


            List<String> kepek = getPictures(path);


            for (String k : kepek) {
                String kepnev = getFilename(k);
                String kephtml = kepnev.replaceAll("jpg","html");
                kephtml = kephtml.replaceAll("png","html");
                out.add("<li><a href=\"" + kephtml + "\">" + kepnev + "</a></li>\n");


            }


            out.add("</ul>\n" +
                  "\t</body>\n" +
                    "</html>");

        System.out.println("File irasa: "+FileName+"\n");
        fajliras(out, FileName);

        }//ha index



    } //makeHtml vege


    ///////////////////////////////////////////// MakeHtml vege, tovabbi metodusok /////////////////////////////////////

    private static int calculatePoints(String fokonyvtar) {
        int sum = 0;

        for(char c : fokonyvtar.toCharArray()){
            if(c == '/') sum++;
        }
        System.out.println("pontok szama: "+sum);
        return -sum;
    }

    private static List<String> getSubFolders(String path) {

        List<String> withoutPics = new ArrayList<>();
        List<String> subs = new ArrayList<>();

        for (String e:DirScan.pathok){
            if(DirScan.isPic(e) == false ) withoutPics.add(e);
        }
        //Lista amiben csak a mappak vannak
        int index = withoutPics.indexOf(path)+1;
        if(path.equals(withoutPics.get(withoutPics.size()-1))){

        }
        else
        {

            while(true){
                if(withoutPics.get(index).endsWith("/")) break;
                index++;

                subs.add(withoutPics.get(index));

            }
        }

        return  subs;

    }//getSubFolders vege


    private static List<String> getPictures(String path) {


        List<String> pics = new ArrayList<>();

        for(String s : Main.duplaknelkul){
            if(path.equals(s)){
                int index = Main.duplaknelkul.indexOf(s);
                do{
                    if(DirScan.isPic(Main.duplaknelkul.get(index)) == true) pics.add(Main.duplaknelkul.get(index));
                    index++;
                    if(index == Main.duplaknelkul.size()) break;
                }while(DirScan.isPic(Main.duplaknelkul.get(index)) == true);

            }
        }

        return  pics;

    }//getPictures vege


    private static String getMappaIndex(String path) {



        String mappa = path.replaceAll("/", "_");
        String[] array = mappa.split("_");
        mappa = array[array.length - 2];



        return mappa;

    }//getMappaIndex



    private static String getPrevPic(String thispath) {

        String prev= "";
        int i = 0;

        for (String e : DirScan.pathok){

            if(e.equals(thispath)){

                if(i != 0){

                    if(DirScan.isPic(DirScan.pathok.get(i-1)) == true){
                        prev = DirScan.pathok.get(i-1);
                    }
                    else{
                        prev = thispath;
                    }
                }

            }

            i++;
        }

        prev = prev.replaceAll(".jpg",".html");
        prev = prev.replaceAll(".png",".html");
        prev = getFilename(prev);
        return prev;

    }//getPrev vege



    private static String getNextPic(String thispath) {

        String next= "";
        int i = 0;

        for (String e : DirScan.pathok){

            if(e.equals(thispath)){

                if(i != DirScan.pathok.size()-1){

                    if(DirScan.isPic(DirScan.pathok.get(i+1)) == true){
                        next = DirScan.pathok.get(i+1);
                    }
                    else{
                        next = thispath;
                    }
                }

            }

            i++;
        }

        next = next.replaceAll(".jpg",".html");
        next = next.replaceAll(".png",".html");
        next = getFilename(next);
        return next;

    }//getNext vege

    private static String getFilename(String path) {


        String name = path.replaceAll("/", "_");
        String[] carray = name.split("_");
        name = carray[carray.length - 1];


        return name;

    }//getFilename vege




    public static void fajliras(List<String> out, String Filename)
    {

        try (PrintWriter pr = new PrintWriter(Filename))
        {

            for (String s: out){
                pr.println(s);
            }

        }
        catch (FileNotFoundException e)
        {

        }


    }//fajlok letrehozasa



}//vegevege

