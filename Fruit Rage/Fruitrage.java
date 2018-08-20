//home work 2

import java.io.*;
import java.util.*;


class Fruitrage{
	
	public static void main(String args[]){
		try{
			long startTime = System.currentTimeMillis();
			Scanner sc = new Scanner(new File("input.txt"));
			int n = sc.nextInt();
			int fruits = sc.nextInt();
			double giventime = sc.nextDouble();
			giventime =giventime * 1000;
			//System.out.println(time);
			int flag=0;
			boolean result = true;

			int matrix [][]= new int[n][n];
			int remfruits=0;
			String r=sc.next(); // reading first number in the matrix
			while(sc.hasNext()){
				r = r+ sc.next();	
			}
			char[] o =r.toCharArray();
			int k=0;
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++)
				{
					if(o[k]=='*')
					{
					matrix[i][j]=99;
					k++;
					}
					else
					{	remfruits++;
						matrix [i][j] = Character.getNumericValue(o[k++]);
					}
					if(matrix[i][j]==2)
						flag =1;				
				}
				
		int finaldepth = 3;
		String caller = "main";
		
		if(giventime<=500 && n>10)
			finaldepth=1;
		else
			if(giventime<=1500 && n>10)
				finaldepth=2;	
		System.out.println(finaldepth);	
		int answer = maxplay(caller,matrix,0,0,-1000,finaldepth);		//0 is the starting depth, return Malpha with adding row+col+value
			
		System.out.println("Score is "+answer);
			
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt",true));

		//writer.write(caller);
		writer.newLine();
			for(int i=0;i<n;i++)
			{	for(int j=0;j<n;j++)
					if(matrix[i][j]==99)
					{
						writer.write("*");
						}
					else
						writer.write(Integer.toString(matrix[i][j]));
				if(i<n-1)
				writer.newLine();
			}
		writer.close(); /// important to close
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		
	/*	Scanner sc1 = new Scanner(new File("output.txt"));
		
		while(sc1.hasNext()){

			String r1=sc1.next();
			System.out.println(r1);
		}
		*/
		}
		catch(Exception e){}
	
	}




	static int maxplay(String caller, int a[][], int depth, int beta, int mbeta, int finaldepth){
	
		int n = a[0].length;
		int v[][]=new int[n][n];
		int g[][]=new int[n][n];		//g for gravity
		
		int flag=0;
		for(int i=0;i<n;i++)
			{
				System.arraycopy(a[i], 0, v[i], 0, a[0].length);
			}

		int malpha = -1000;
		int alpha=0;
		int count = 0;
		int alphax=0;
		int rfinal=0;
		int cfinal=0;
		
		for(int r=0;r<n;r++){
			for(int c=0;c<n;c++){
				
				for(int i=0;i<n;i++)
				{
					System.arraycopy(a[i], 0, g[i], 0, a[0].length);
				}
				
		//		System.out.println("Max");
		//		System.out.println(c);
				flag=0;
				
				if(a[r][c]!=99 && v[r][c]!=99){
					count = 1;
					Queue<Integer> q = new LinkedList<Integer>();
					q.add(r);
					q.add(c);
					
					while(!q.isEmpty()){
					//	System.out.println("Q size b4 pop- "+q.size()/2);
						int r1=q.poll();
						int c1=q.poll();
						int r2=r1;		//r2 for gravity
						
					//	System.out.println("Q size after pop- "+q.size()/2);
					//	count ++;
						v[r1][c1]=99;
						
						g[r1][c1]=99;
						
						while(r2>0 && g[r2-1][c1]!=99){    //gravity
				//		System.out.println("gr");
							g[r2][c1]=g[r2-1][c1];
							r2--;
						}
						g[r2][c1]=99;
						
					if(r1<n-1){
					
						if(a[r1][c1]==a[r1+1][c1] && v[r1+1][c1]!=99 ){		//check element below
							q.add(r1+1);
							q.add(c1);	
								count ++;
								v[r1+1][c1]=99;
						//	System.out.println("Q size for below element- "+q.size());
						}
					}
						
					if(c1<n-1){
					
						if(a[r1][c1]==a[r1][c1+1] && v[r1][c1+1]!=99 ){ 		//check element next
							q.add(r1);
							q.add(c1+1);
							count ++;
							v[r1][c1+1]=99;
						//	System.out.println("Q size for next element- "+q.size());		
						}
					}
					
					if(c1>0){
					
						if(a[r1][c1]==a[r1][c1-1] && v[r1][c1-1]!=99 ){		//check element back
							q.add(r1);
							q.add(c1-1);		
								count ++;
								v[r1][c1-1]=99;
						}
					}
					
					if(r1>0){
					
						if(a[r1][c1]==a[r1-1][c1] && v[r1-1][c1]!=99 ){		//check element above
							q.add(r1-1);
							q.add(c1);	
								count ++;
								v[r1-1][c1]=99;
						}
					}
				}  		/// END OF WHILE
				
			//	System.out.println(count);
	/*				for(int i=0;i<n;i++)
					{for(int j=0;j<n;j++)
							System.out.print(g[i][j]);
					System.out.println();
					}
	*/				

					
					flag=1;
				}						/// END IF
		
				
				if(flag==1){
					
					//alpha = count;
					
					depth ++;
					
					if(depth==finaldepth || boardempty(g))
					{	if(count>alpha)
							alpha=count;
						
						int diff=alpha;
						if(diff>malpha){
							malpha = diff;
							alphax = alpha;
							rfinal = r;
							cfinal = c;
						}
					
						depth--;
					}
					else{
						
					alpha = count;
					
					if(mbeta>(beta-alpha))
						continue;
					//System.out.println(alpha);							
					int b1= playerchoose("max",g,depth,alpha,malpha,finaldepth);
				//	System.out.println("Min returned - "+b1);
					depth--;				//necessary when expanding other branches	
					int diff1 = alpha - b1;
																				/*		for(int i=0;i<n;i++)
																							{for(int j=0;j<n;j++)
																									System.out.print(a[i][j]);
																							System.out.println();
																							}
			*/
					if(diff1 > malpha)
					{
						malpha = diff1;
						alphax=alpha;
					//	System.out.println(alphax);
						rfinal=r;
						cfinal=c;
						}
			
				
					}
				}
			}				/// END FOR j
		}			// END FOR i
		
		if(caller.equals("main")){

			System.out.println(rfinal+"  "+cfinal);
			try{
			BufferedWriter writer1 = new BufferedWriter(new FileWriter("output.txt"));
			char col = (char)(cfinal+65);
			int row = rfinal+1;
			writer1.write(col+""+row);
			writer1.close();
			
			}
			catch(Exception e){}

			makefinalchange(rfinal,cfinal,a);
						/*		for(int i=0;i<n;i++)
					{for(int j=0;j<n;j++)
							System.out.print(g[i][j]);
					System.out.println();
					}*/
					System.out.println(alphax);
			return alphax;
		}
		else
			return alphax;		//return my max score to opponent
	}
	
	
	
	static int playerchoose(String player, int a[][], int depth, int val, int mval, int finaldepth){
		
		if(player.equals("max"))
			return minplay("max",a,depth,val,mval,finaldepth);
		else
			return maxplay("min",a,depth,val,mval,finaldepth);
		}
	
	
	
	static int minplay(String caller, int a[][], int depth, int alpha, int malpha, int finaldepth){
	
		int n = a[0].length;
		int v[][]=new int[n][n];
		int g[][]=new int[n][n];		//g for gravity

		int flag=0;
		for(int i=0;i<n;i++)
		{
			System.arraycopy(a[i], 0, v[i], 0, a[0].length);
		}
			
		//System.out.println(n);
		int mbeta = -1000;
		int beta=0;
		int count = 0;
		int betax=0;
		int rfinal=0;
		int cfinal=0;
		
		
		for(int r=0;r<n;r++){
			for(int c=0;c<n;c++){
				
				for(int i=0;i<n;i++)
				{
					System.arraycopy(a[i], 0, g[i], 0, a[0].length);
				}
		//		System.out.println("Min");
				flag=0;
				if(a[r][c]!=99 && v[r][c]!=99){
					count = 1;
					Queue<Integer> q = new LinkedList<Integer>();
					q.add(r);
					q.add(c);
					
					while(!q.isEmpty()){
					//	System.out.println("Q size b4 pop- "+q.size()/2);
						int r1=q.poll();
						int c1=q.poll();
						int r2=r1;		//r2 for gravity
						
					//	System.out.println("Q size after pop- "+q.size()/2);
					//	count ++;
						g[r1][c1]=99;
						v[r1][c1]=99;
						  
						
						while(r2>0 && g[r2-1][c1]!=99){    //gravity
				//		System.out.println("gr");
							g[r2][c1]=g[r2-1][c1];
							r2--;
						}
						g[r2][c1]=99;
						
					if(r1<n-1){
					
						if(a[r1][c1]==a[r1+1][c1] && v[r1+1][c1]!=99 ){		//check element below
							q.add(r1+1);
							q.add(c1);	
								count ++;
								v[r1+1][c1]=99;
						//	System.out.println("Q size for below element- "+q.size());
						}
					}
						
					if(c1<n-1){
					
						if(a[r1][c1]==a[r1][c1+1] && v[r1][c1+1]!=99 ){ 		//check element next
							q.add(r1);
							q.add(c1+1);
							count ++;
							v[r1][c1+1]=99;
						//	System.out.println("Q size for next element- "+q.size());		
						}
					}
					
					if(c1>0){
					
						if(a[r1][c1]==a[r1][c1-1] && v[r1][c1-1]!=99 ){		//check element back
							q.add(r1);
							q.add(c1-1);		
								count ++;
								v[r1][c1-1]=99;
						}
					}
					
					if(r1>0){
					
						if(a[r1][c1]==a[r1-1][c1] && v[r1-1][c1]!=99 ){		//check element above
							q.add(r1-1);
							q.add(c1);	
								count ++;
								v[r1-1][c1]=99;
						}
					}
				}  		/// END OF WHILE

		/*			for(int i=0;i<n;i++)
					{for(int j=0;j<n;j++)
							System.out.print(g[i][j]);
					System.out.println();
					}
			*/	
					flag=1;
				}						/// END IF
				///////////////////////////////////////////////////////
				if(flag==1){
		//		System.out.println("Min count - "+count);
				//	beta = count;
					
					depth ++;
		//			System.out.println("Min depth  - "+depth);
					if(depth==finaldepth || boardempty(g))
					{ 
						if(count>beta)
							beta = count;
							
						int diff = beta;
						
						if(diff > mbeta)
						{
							mbeta = diff;
							betax=beta;
							rfinal=r;		//for min r,c not necessary
							cfinal=c;
							}
							depth--;
					}
					else
					{
						
					beta=count;	
					if(malpha>(alpha-beta))
						continue;			//it shud be continue
						
					
					int a1= playerchoose("min",g,depth,beta,mbeta,finaldepth);
		/*			System.out.println("Max returned - "+a1);
																						for(int i=0;i<n;i++)
																							{for(int j=0;j<n;j++)
																									System.out.print(a[i][j]);
																							System.out.println();
																							}
		*/	
					depth--;	
					int diff1 = beta - a1;
					
					if(diff1 > mbeta)
					{
						mbeta = diff1;
						betax=beta;
						rfinal=r;		//for min r,c not necessary
						cfinal=c;
						}
				///////////////////////////////////////////////////////////
					}
				}	
			}				/// END FOR j
		}			// END FOR i
		
	
		return betax;
	}	
		
	static boolean boardempty(int g[][]){
		for(int i=0;i<g[0].length;i++)
			for(int j=0;j<g[0].length;j++){
				if(g[i][j]!=99)
					return false;
			}
		return true;
	}
	
	static void makefinalchange(int r, int c, int a[][]){
		
		int n = a[0].length;
		int v[][]=new int[n][n];
		int g[][]=new int[n][n];		//g for gravity
		
		int flag=0;
		for(int i=0;i<n;i++)
		{
			System.arraycopy(a[i], 0, v[i], 0, a[0].length);
			System.arraycopy(a[i], 0, g[i], 0, a[0].length);
		}
		
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(r);
		q.add(c);
		
		while(!q.isEmpty()){
					//	System.out.println("Q size b4 pop- "+q.size()/2);
						int r1=q.poll();
						int c1=q.poll();
						int r2=r1;		//r2 for gravity
						
					//	System.out.println("Q size after pop- "+q.size()/2);
					//	count ++;
						g[r1][c1]=99;
						v[r1][c1]=99;
						  
				/*		
						while(r2>0){    //gravity
				//		System.out.println("gr");
							g[r2][c1]=g[r2-1][c1];
							r2--;
						}
						g[r2][c1]=99;
						*/
					if(r1<n-1){
					
						if(a[r1][c1]==a[r1+1][c1] && v[r1+1][c1]!=99 ){		//check element below
							q.add(r1+1);
							q.add(c1);	
							//	count ++;
								v[r1+1][c1]=99;
						//	System.out.println("Q size for below element- "+q.size());
						}
					}
						
					if(c1<n-1){
					
						if(a[r1][c1]==a[r1][c1+1] && v[r1][c1+1]!=99 ){ 		//check element next
							q.add(r1);
							q.add(c1+1);
						//	count ++;
							v[r1][c1+1]=99;
						//	System.out.println("Q size for next element- "+q.size());		
						}
					}
					
					if(c1>0){
					
						if(a[r1][c1]==a[r1][c1-1] && v[r1][c1-1]!=99 ){		//check element back
							q.add(r1);
							q.add(c1-1);		
						//		count ++;
								v[r1][c1-1]=99;
						}
					}
					
					if(r1>0){
					
						if(a[r1][c1]==a[r1-1][c1] && v[r1-1][c1]!=99 ){		//check element above
							q.add(r1-1);
							q.add(c1);	
						//		count ++;
								v[r1-1][c1]=99;
						}
					}
				}  		/// END OF WHILE
			
		for(int i=0;i<n;i++)
		{	for(int j=0;j<n;j++)
				System.out.print(g[i][j]);		
			System.out.println();
		}
		
	gravityfalls(g);
		
	System.out.println("AFTER GRAVITY");		
		
		
		for(int i=0;i<n;i++)
		{	for(int j=0;j<n;j++)
				System.out.print(g[i][j]);		
			System.out.println();
		}
		
		
		for(int i=0;i<n;i++)
			{
				System.arraycopy(g[i], 0, a[i], 0, g[0].length);
				//System.arraycopy(a[i], 0, g[i], 0, a[0].length);
			}
			

	}
	
	static void gravityfalls(int g[][]){
		int n=g[0].length;
	
		for(int i=0;i<n;i++)
		{	for(int j=0;j<n;j++)
			{
				if(i==0)
					continue;
				else
					if(g[i][j]==99)
					{
						int ro = i;
						while(ro>0){
							g[ro][j]=g[ro-1][j];
							g[ro-1][j]=99;
							ro--;
						}
					}
				}	
			}
		}
}
