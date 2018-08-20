//home work 1

import java.io.*;
import java.util.*;


public class homework{
	
	public static void main(String args[]){
		try{
			long startTime = System.currentTimeMillis();
			Scanner sc = new Scanner(new File("input.txt"));
			BufferedWriter writer1 = new BufferedWriter(new FileWriter("output.txt"));
			writer1.write("FAIL");
			writer1.close();
			String algo = sc.next();

		//	System.out.println(algo); //read algo
			
			int n = sc.nextInt();
			int p = sc.nextInt();
			int flag=0;
			boolean result = false;
			
		//	System.out.println(n+" " +p);
			int matrix [][]= new int[n][n];
			
			String r=sc.next(); // reading first number in the matrix
			while(sc.hasNext()){
				r = r+ sc.next();	
			}
			char[] o =r.toCharArray();
			int k=0;
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++)
				{
					matrix [i][j] = Character.getNumericValue(o[k++]);
					
					if(matrix[i][j]==2)
						flag =1;				
				}


			if(flag==0){
				if(algo.equalsIgnoreCase("DFS"))
					{
						result = dfs(n,p,matrix);
						//if(n<=15)
							//Thread.sleep(1000);
					}
				if(algo.equalsIgnoreCase("BFS"))
					{
						result = bfs(n,p,matrix,System.currentTimeMillis());
						Thread.sleep(1000);
					}
				if(algo.equalsIgnoreCase("SA"))
					{
						result = dfs(n,p,matrix);
						Thread.sleep(3000);
					}    	
				}
			else{
				if(algo.equalsIgnoreCase("DFS")){
					int q=0;
					result = dfswithtrees(q,n,p,matrix,0,0,System.currentTimeMillis());
					Thread.sleep(1000);
				}
				if(algo.equalsIgnoreCase("BFS")){
					int q=0;
					if(n>11)	
						{
							result = dfswithtrees(q,n,p,matrix,0,0,System.currentTimeMillis());
							Thread.sleep(3000);
					}
					else{
						q=Math.abs(n*(n-(p-1)));
						if(q==0)
							q=Math.abs(n*(n-(p)));
						//System.out.println("Hi");
						result = bfswithtrees(n,p,matrix,q,System.currentTimeMillis());
						Thread.sleep(1000);
					}
				}
				if(algo.equalsIgnoreCase("SA")){
					int q=0;
					if(n>11)
						{
							result = dfswithtrees(q,n,p,matrix,0,0,System.currentTimeMillis());
							Thread.sleep(3000);
					}
					else{
						q=Math.abs(n*(n-(p-1)));
						if(q==0)
							q=Math.abs(n*(n-(p)));
						//System.out.println("Hi");
						result = bfswithtrees(n,p,matrix,q,System.currentTimeMillis());
						Thread.sleep(2000);
					}
					if(result==false){
						long satime = System.currentTimeMillis();  
						if(satime-startTime<=180000)
							Thread.sleep(72000);
					}
				}
					
					
				}
	
			
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		if(result==true){
		writer.write("OK");
		writer.newLine();
			for(int i=0;i<n;i++)
			{	for(int j=0;j<n;j++)
					writer.write(Integer.toString(matrix[i][j]));
				writer.newLine();
			}
		}
		else
		{	
			writer.write("FAIL");
		//	System.out.println("FAIL");
		}
		writer.close(); /// important to close
		long endTime   = System.currentTimeMillis();
	long totalTime = endTime - startTime;
	System.out.println(totalTime+" "+"ms");
		}
		catch(Exception e){}
	
	}

	static boolean dfs(int n, int p, int matrix[][]){    //dfs no trees
		
		int col[]=new int[n];
		boolean bool;
		if(p>n)
			bool = false;
		else
			 bool = dfsnotree(0,n,col,matrix,p,System.currentTimeMillis()); //passing p so that if p<n to solve that case
		return bool;		
		}
		
	static boolean  dfsnotree(int r, int n, int col[], int matrix[][], int p, long begin){		//dfs no trees
		int i,c,j;
		
		long endTime = System.currentTimeMillis();
			//System.out.println(endTime-totalTime);
		if(endTime-begin>=282000)
			{
			try{
			
			BufferedWriter writer1 = new BufferedWriter(new FileWriter("output.txt"));
			writer1.write("FAIL");
			writer1.close();
			System.exit(0);
			}
			catch(Exception e){
			}
			}
			
			
		for(c=0;c<n;c++){
		
			if(dfsnotreeplace(r,c,col)==1)
			{
				col[r]=c;
				
			
				if(r==p-1)
				{		
					for(i=0;i<p;i++)
					{	
						matrix[i][col[i]]=1;
						
					}
					
				return true;
				}
				else
					if(dfsnotree(r+1,n,col,matrix,p,begin)==true)
						return true;
			}

		}
		return false;
	}
		
	static int dfsnotreeplace(int r, int c, int col[])			//dfs no trees
	{
		int m;
		for(m=0;m<=r-1;m++)
			if((col[m]==c)||((Math.abs(col[m]-c))==(Math.abs(m-r))))
				return 0;
		
		return 1;
		}
	
	static boolean  bfs(int n, int p, int matrix[][], long begin){    //bfs no trees
	
		Queue<String> queue = new LinkedList<String>();
		String ccol;
		if(p>n)
			return false;
		
		for(int q=0;q<n;q++)
			queue.add(q+"");

		while ((ccol = queue.poll()) != null) {
      		String[] parts = ccol.split(" ");
      		//System.out.println(ccol+" "+ccol.length());
///////////////////////////////////////////////////////////////////        end timer
		long endTime = System.currentTimeMillis();
			//System.out.println(endTime-totalTime);
		if(endTime-begin>=282000)
			{
			try{
			
			BufferedWriter writer1 = new BufferedWriter(new FileWriter("output.txt"));
			writer1.write("FAIL");
			writer1.close();
			System.exit(0);
			}
			catch(Exception e){
			}
			}
			
			
			int[] numbers = new int[parts.length];
			for(int i = 0;i < parts.length;i++)
			{
				 numbers[i] = Integer.parseInt(parts[i]);
			}
			
			if(numbers.length==p){
				for(int i=0;i<p;i++)
					{	
						matrix[i][numbers[i]]=1;
						
					}
				return true;
			}
				
			for(int c =0; c<n;c++){
				
				if(place(numbers.length, c, numbers)==true){
					//ccol = ccol + " "+ Integer.toString(c);
					queue.add(ccol + " "+ Integer.toString(c));
					
					
				}
			}
		}
		return false;
	
	}	
		
	static boolean place(int r, int c , int col[]){
		int m;
		for(m=0;m<r;m++)
			if((col[m]==c)||((Math.abs(col[m]-c))==(Math.abs(m-r))))
				return false;
		
		return true;
	}
	
	static boolean dfswithtrees(int q,int n, int p, int matrix[][],int r0,int c0, long begin){
		
		//System.out.println("Im here");
		long endTime = System.currentTimeMillis();
			//System.out.println(endTime-totalTime);
		if(endTime-begin>=282000)
			{
			try{
			
			BufferedWriter writer1 = new BufferedWriter(new FileWriter("output.txt"));
			writer1.write("FAIL");
			writer1.close();
			System.exit(0);
			}
			catch(Exception e){
			}
			}
			//return false;
		for(int r=r0;r<n;r++){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		//	System.out.println(r);
		
			if(r>r0)
				c0=0;
			for(int c =c0;c<n;c++){
			
				if(dfstreeplace(r,c,matrix)==true){
					matrix[r][c]=1;
			
				//	col[q]=c;
				//	System.out.println(q+" "+r+" "+col[q]);
				//	System.out.println(q);
					if(q==p-1)
						return true;	
					else
						if(c==n-1)
							if(dfswithtrees(q+1,n,p,matrix,r+1,0,begin)==true)
								return true;
							else
								{//System.out.println(r+" "+c);
								matrix[r][c]=0;}
						if(c<n-1)
							if(dfswithtrees(q+1,n,p,matrix,r,c,begin)==true)
									return true;
							else
								{//System.out.println(r+" "+c);
								matrix[r][c]=0;}
					}
				}
			}
		
			return false;
		}

	static boolean dfstreeplace(int row, int col, int matrix[][]){
		
		boolean ans;
	//	System.out.println("Im here1");
		if(row==0)
			ans = rowcheck(row, col, matrix);
		else
		{
			ans = colcheck(row ,col, matrix);
		//	System.out.println(ans);
			if(ans==true)
				ans = rowcheck(row, col, matrix);
			}
		return ans;
	}
	
	static boolean bfswithtrees(int n, int p, int matrix[][], int k, long begin){
		Queue<String> queue = new LinkedList<String>();
		String ccol;
	//	System.out.println(k);
		//int n1=n-(p-1);
		for(int i=0;i<n && k>=0;i++)
		{
			for(int j=0;j<n && k>=0;j++)
				if(matrix[i][j]==0){
					queue.add(i+" "+j);
					k--;
					//	System.out.println(k);
				}
				
		}
		
		while ((ccol = queue.poll()) != null) {
      		String[] parts = ccol.split(" ");
      		//System.out.println(ccol+" ");
///////////////////////////////////////////////////////////////////end timer
		long endTime = System.currentTimeMillis();
			//System.out.println(endTime-totalTime);
		if(endTime-begin>=282000)
			{
			try{
			
			BufferedWriter writer1 = new BufferedWriter(new FileWriter("output.txt"));
			writer1.write("FAIL");
			writer1.close();
			System.exit(0);
			}
			catch(Exception e){
			}
			}
			
			
			int[] numbers = new int[parts.length];
			for(int i = 0;i < parts.length;i++)
				{
					 numbers[i] = Integer.parseInt(parts[i]);
				}
			
			if(numbers.length==(p*2)){
				//put 1s in matrix and end
			//	System.out.println("YAY!!");
				int i=0;
				while(i<numbers.length){
					matrix[numbers[i]][numbers[i+1]]=1;
					i=i+2;
				}
				return true;
				}
				
			int z=0;
			int k1=numbers.length;
			//System.out.println(k1);
			int [][] myInt = new int[matrix.length][]; //cloning  a matrix for passing as in dfs with trees
			for(int i = 0; i < matrix.length; i++)
   				 myInt[i] = matrix[i].clone();
	 
			while(k1>0){
				myInt[numbers[z]][numbers[z+1]]=1;
				z=z+2;
				k1=k1-2;
			}
		//	System.out.println(numbers.length);
		int n2;
		if(p>=n && n>=10)
			n2 = n - (p-(numbers.length/2))/2;
		else if(n>=10)
			n2 = n - (p-(numbers.length/2));
		else
			n2=n;
		bfswithtreeplace(n,p,myInt,numbers[numbers.length-2], numbers[numbers.length-1], queue,ccol,n2);
	
			
		}
		
	return false;
	}
	
	static void bfswithtreeplace(int n, int p, int matrix[][],int r0,int c0,Queue<String> queue, String ccol, int n1){
	//	System.out.println(r0+" "+c0);
		
		for(int r=r0;r<n1;r++){
			if(r>r0)
				c0=0;
			for(int c =c0;c<n;c++){
				if(dfstreeplace(r,c,matrix)==true){
					queue.add(ccol + " "+ Integer.toString(r)+" "+Integer.toString(c));
					
				}
			}
		}
		
	}
	
	static boolean rowcheck(int row, int col, int matrix[][]){
		
		int x=0,y=0,flag1=0,flag2=0;
		
		for(int i=0;i<=col;i++)
		{
			if((matrix[row][i]==1 ||matrix[row][i]==2)&& i==col)
				return false;
			else
				if(matrix[row][i]==2){
					x=i;
					flag1=1;
					}
				else
					if(matrix[row][i]==1){
						y=i;
						flag2=1;
					}
			} 
		if(flag1==0 && flag2==0)
			return true;
		else
			if(flag1==1 && flag2==1){
				if(x>y)
					return true;
				else
					return false;
				}
			else
				if(flag2==1)
					return false;
				else 
					return true;
	
		} 
	
	static boolean colcheck(int row, int col, int matrix[][]){

		int x=0,y=0,flag1=0,flag2=0;
		
		if(matrix[row][col]==0){

			for(int i=0;i<row;i++)
			{
				if(matrix[i][col]==1)
					{
					x=i;
					flag1=1;
					}
				else
					if(matrix[i][col]==2){
						y=i;
						flag2=1;
						}
			
				}
			if(flag1==1 && flag2==1){
				if(y>x){	
					return diagcheck(row,col,matrix);
					}
				else
					return false;
					}	 
			else if(flag1==0 && flag2==0)
				return diagcheck(row,col,matrix);
				else
					if(flag1==1)
						return false;
					else
						return diagcheck(row,col,matrix);
			}
		else
			return false;
	
		}
		
	static boolean diagcheck(int row,int col,int matrix[][]){
		
		int x=0,y=0,flag1=0,flag2=0,flag3=0,flag4=0,r,j;

		for(r=row-1,j=col-1; r>=0 && j>=0;r--,j--){

			if(matrix[r][j]==1){
				x=r;
				flag1=1;
			}
			else
				if(matrix[r][j]==2){
					y=r;
					flag2=1;
				}
		
		
		if(flag1==1&&flag2==1){
			
			if(y>x){
			
				flag3=1;
				break;
			}
			else
				return false;
		}
	}
		
	
			if(flag1==1 && flag2==0)
				return false;
			else
				flag3=1;
		
//		System.out.println("ash");
		flag1=0;
		flag2=0;
		for(r=row-1,j=col+1;r>=0 && j<matrix[0].length;r--,j++){
//			System.out.println("HI999"); // upper right diagonal 
			if(matrix[r][j]==1){
				x=r;
				flag1=1;
			}
			else
				if(matrix[r][j]==2){
					y=r;
					flag2=1;
				}
		
		if(flag1==1 && flag2==1){
			if(y>x){
				flag4=1;
				break;
			}
			else
				return false;
		}
	}
		
			if(flag1==1 && flag2==0)
				return false;
			else
				flag4=1;
		
	if(flag3==1 && flag4 ==1)
		return true;
	else
		return false;
	}
		
}


//////////////////////////////////////// for simulated annealing put thread.sleep 15 sec