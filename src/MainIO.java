import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MainIO {


    public static void listing_files(String filename) {

        File f = new File(filename);
        if(f.exists()) {
            if(!f.isDirectory()) {
                System.out.println(f.getAbsolutePath());
            }
            else {
                System.out.println(f.getAbsolutePath());

                File [] files = f.listFiles();
                for(int i =0;i<files.length;i++)
                    listing_files(files[i].getAbsolutePath());
            }
        }




    }


    public static void fileExamples() {


        File f = new File("ornek klasor");
        f.mkdir();
        System.out.println(f.getAbsolutePath());
        System.out.println(f.getParent());






    }




    public static void writer() {

        PrintWriter out = null;

        try {
            out = new PrintWriter(new FileOutputStream("dosya.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println("1 2 3");


        out.print("1234");
        out.close();
        System.out.println("program bitti");


    }

    public static void binary_reader() {
        ObjectInputStream i = null;
        try {
            i = new ObjectInputStream(new FileInputStream("binary_file"));

            /*System.out.println(i.readShort());
            System.out.println(i.readShort());
            System.out.println(i.readShort());

            System.out.println(i.readByte());*/
            //System.out.println(i.readInt());

            //System.out.println(i.readFloat());
            //System.out.println(i.readDouble());
            System.out.println(i.readUTF());
            System.out.println(i.readUTF());
            //System.out.println(i.readBoolean());
            //System.out.println(i.readByte());


            i.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //	System.out.println("program bitti");
    }

    public static void read_each_byte(String filename) {

        ObjectInputStream i = null;
        try {
            i = new ObjectInputStream(new FileInputStream(filename));

            while(true) {
                System.out.print(i.readByte() + " ");
            }
        }
        catch(EOFException e) {
            try {
                i.close();
            } catch (IOException e1) {
            }
        }
        catch (IOException e) {
        }
        System.out.println();
    }



    public static void binary_writer() {
        String filename = "binary_file";
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(new FileOutputStream(filename));

            //o.writeInt(257); //8x0 8x0 00000001 00000001
            //o.writeShort(8);  // 8x0 00001000
            //o.writeFloat(0.3f);
            //o.writeDouble(0.3);
            o.writeUTF("abc");
            o.writeUTF("efg");
            //o.writeBoolean(true);
            //o.writeByte(5); // 00000101


            o.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        binary_reader();

        //System.out.println("program bitti");
    }


    public static void reader() {

        Scanner s = null;

        try {
            s = new Scanner(new FileInputStream("dosya.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(s.nextLong());
        /*
        while(s.hasNextLine())
        System.out.println(s.nextLine());*/
        /*
		while(s.hasNextInt())
		{
			int tmp = s.nextInt();
			System.out.println(tmp);
		} */
        //System.out.println(s.nextInt());
        //System.out.println(s.next());
        /*
        s = s.useDelimiter(",");
        while(s.hasNext())
            System.out.println(s.next()); */
        s.close();

    }

    public static void reader_with_bufferedreader() {

        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new FileReader("dosya.txt"));

            // satır satır dosya okuma
            String line = bf.readLine();
            while(line != null) {
                //System.out.println(line);
                StringTokenizer st = new StringTokenizer(line);
                while(st.hasMoreTokens())
                    System.out.println(st.nextToken());
                line = bf.readLine();
            }

            // karakter karakter okuma
            int x = bf.read();
            while(x != -1) {
                System.out.print((char)x);
                bf.skip(1);
                x = bf.read();

            }

            System.out.println();


            bf.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {

        //writer();
        writer();
        //reader();
        //binary_writer();
        //binary_reader();
        //reader_with_bufferedreader();
        //fileExamples();
        //listing_files("C:\\Users\\bilge\\OneDrive\\Belgeler\\Python Scripts");
        System.out.println("program bitti");

    }

}
