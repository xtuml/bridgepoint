import java.io.File;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File file = new File("D:\\eclipse\\workspaces\\PLCM_NEW");
		iterate(file);
	}
	
	private static void iterate(File file){
		File[] children = file.listFiles();
		for (int i = 0; i < children.length; i++) {
			if(isCVSFolder(children[i])){
				checkCVSFolder(children[i]);
			}else if(children[i].isDirectory()){
				iterate(children[i]);
			}
		}
	}
	
	private static void checkCVSFolder(File cvsFolder){
		File[] cvsFiles = cvsFolder.listFiles();
		if(cvsFiles.length < 4 || cvsFiles.length > 4){
			System.err.print(cvsFolder + "[");
			for (int i = 0; i < cvsFiles.length; i++) {
				if(i>0){
					System.err.print(";");
				}
				System.err.print(cvsFiles[i]);
			}
			System.err.println("]");
		}
	}
	
	private static boolean isCVSFolder(File dir){
		if(dir.isDirectory() && dir.getName().equals("CVS")){
			return true;
		}
		return false;
	}

}
