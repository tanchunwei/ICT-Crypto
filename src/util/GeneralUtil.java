/* Project Title:	IT3119 Information Security Case Study
 * Project Group:	05
 * Author:			Tan Chun Wei
 */

package util;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GeneralUtil {
	
	
	public static boolean checkFileName(String filename){
		if(filename.contains(".")){
			return true;
		}else{
			return false;
		}
	}
	
	public static Icon getFileIcon(String filename) throws FileNotFoundException{
		 File file = new File(filename);

	    // Get metadata and create an icon
	    sun.awt.shell.ShellFolder sf =
	            sun.awt.shell.ShellFolder.getShellFolder(file);
	    Icon icon = new ImageIcon(sf.getIcon(true));
	    return icon;
	}
	
	public static String filterFileExtension(String filename){
		return filename.split("\\.")[0];
	}
	
	public static long getFileSize(String filename) {
		 File file = new File(filename);
		    
		 if (!file.exists() || !file.isFile()) {
			 //System.out.println("File doesn\'t exist");
			 return -1;
		 }
		    
		 //Here we get the actual size
		 return file.length();
	}
	
	public static String getFileNameByURL(String url){
		char c ;
	    String cname = "";
	    for (int i = url.length()-1; i >= 0; i--){
	    	c = url.charAt(i);
	    	if (c == '\\'  || c == '/'){
	    		break;
	    	}
	    	else{
	    		cname = c + cname;
	    	}
	    }
	    
	    return cname;
	}
	
	public static String convertUrlToJavaUrl(String url){
		
	    char c ;
	    String cname = "";
	    for (int i = 0; i < url.length(); i++){
	    	c = url.charAt(i);
	    	if (c == '\\' ){
	    		cname = cname + "\\\\";
	    	}
	    	else{
	    		cname = cname + c;
	    	}
	    }
	    
	    return cname;
	}
	
	public static String convertUrl(String url){
		String arr[] = url.split("\\(");
		String url1 = "";
		String arr1[] = arr[1].split("\\)");
		
		url1 += arr[0];
		url1 += arr1[1];
		
		return url1;
	}
	
	public static String replaceUrlFileName(String url, String fileName){
		String url1 = "";
		String temp = null;
		Scanner sc = new Scanner(url);
	
		//System.out.println(url);
		sc.useDelimiter("\\\\");
		
		
		while(sc.hasNext()){
			temp = sc.next();
			
			if(!sc.hasNext()){
				String arr[] = temp.split("\\.");
				
				arr[0] = arr[0]+ fileName;
				url1 += arr[0] + "." + arr[1];
			}else{
				url1 += temp + "\\";
			}
		}
		return url1;
	}
	
	public static String[] arrayMinusArray(String [] a, String [] b){
		String [] result = new String[a.length - b.length ];
		int unSelectedCount = 0;
		boolean isSame = false;
		
		for(int i =0; i <a.length; i++){
			isSame = false;
			for(int j =0; j <b.length; j++){
				if(a[i].equals(b[j])){
					isSame = true;
				}
			}
			if(!isSame){
				result[unSelectedCount] = a[i];
				unSelectedCount ++;
			}
		}
		
		return result;
	}

	public static void info(String msg, Component comp){
		JOptionPane.showMessageDialog(comp, msg);
	}
	
	public static void main(String [] args){
		System.out.println(getFileNameByURL("C://Users//Tan Chun Wei//Desktop//DemoDocument//EditedByOtherWithoutPermissionDoc(Edited)(Encrypted By CSS).docx"));
	}

}
