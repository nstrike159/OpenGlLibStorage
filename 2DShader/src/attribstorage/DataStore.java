package attribstorage;

public class DataStore {
	private String[][] Data=new String[10][10];
	public DataStore() {
		Data[0][0]="books";
		System.out.println(23%11);
	}
	public boolean SetLimit(int L1,int L2)
	{
		if(L1>1&&L2>1)
		{
		String[][] nData=new String[L1][L2];
		for(int l=0;l<Data.length;l++)
		{
			nData[l]=Data[l];
		}
		Data=nData;
		return true;
		}else{
			return false;
		}
	}
	private int FindLength(String[] T1,String[][] T2){
		int len=0;
		boolean T1F=true;
		boolean T2F=true;
		try{
			if(T1==null){
				T1F=false;
			}
		}catch(NullPointerException e){
			T1F=false;
		}
		try{
			if(T2==null){
				T2F=false;
			}
		}catch(NullPointerException e){
			T2F=false;
		}
		if(T1F){
		String[] tab=T1;
		for(int q=0;q<tab.length;q++)
			if(tab[q]==null){
				len=q;
				break;
			}
		}
		if(T2F)
		{
		String[][] tab=T2;
		for(int q=0;q<tab.length;q++)
			if(tab[q][0]==null){
				len=q;
				break;
			}
		}
		return len;
	}
	public void AddData(String scope,String value)
	{
		boolean found=false;
		if(value!=null)
		{
			for(String[] i:Data)
			if(i[0]==scope)
			{
				found=true;
				int len=FindLength(i,null);
				i[len]=value;
			}
		}
		if(found==false){
			int len=FindLength(null,Data);
			
			Data[len][0]=scope;
			if(value!=null)
				Data[len][1]=value;
		}
	}
	public String[][] GetData(){return Data.clone();}
	public void DeleteDataScope(String scope)
	{
		int len=Data.length;
		for(int e=0;e<len;e++){
			if(Data[e][0].equals(scope)){
				int slen=FindLength(Data[e],null);
				for(int s=0;s<slen;s++)
					Data[e][s]=null;
			}
		}
	}
	public void DeleteDataValue(String value)
	{
		int len=Data.length;
		for(int i=0;i<len;i++){
			int slen=FindLength(Data[i],null);
			for(int v=0;v<slen;v++){
				if(Data[i][v].equals(value)){
					for(int w=v;w<slen;w++){
						Data[i][w]=Data[i][w+1];
					}
					Data[i][slen]=null;
				}
			}
		}
	}
	public String[] FindData(String search)
	{
		String s1=search.toLowerCase();
		
		String[] found = null;
		for(int i=0;i<Data.length;i++){
			//System.out.println(Data[i][0]);
			if(Data[i][0]!=null && s1.compareTo(Data[i][0])==0){
			//	System.out.println("Found "+search);
				found=Data[i].clone();
				break;
			}
		}
		return found;
	}
}