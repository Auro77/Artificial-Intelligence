//home work 3

import java.io.*;
import java.util.*;

class homework{
	static List<Integer> count ;
	public static void main(String[] args){
		
		try{
				//System.out.println("Hello");
				
				Scanner sc = new Scanner(new File("input.txt"));
				//System.out.println("Hello1");
				int nq = sc.nextInt();		//no of queries
				sc.nextLine();
		
				ArrayList<String> Queries = new ArrayList<String>(); 		
		
				//System.out.println(nq);
		
				for(int i=0;i<nq;i++){
					Queries.add(sc.nextLine());
				}
				

			//	System.out.println(Queries);
				int ns = sc.nextInt();   //no of stmts in kb
				

			//	System.out.println(ns);
				String r = sc.next();
				while(sc.hasNext()){
					r += sc.nextLine() + "$";
				}
				
				String s[]= r.split("\\$");
				//for(int i=0;i<ns;i++){
					//System.out.println(r);
				//}
			//	for(int i=0;i<s.length;i++){
			//		System.out.println(s[i]);
			//	}
				
				for(int i=0;i<s.length;i++)
					s[i]=standardize(s[i],i);
					
				// print standardized kb
			//	for(int i=0;i<s.length;i++)
			//	System.out.println(s[i]);
				
		//Main For Loop		
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		
				for(int i=0;i<Queries.size();i++){
					String q = Queries.get(i);
					
					if(q.charAt(0)=='~')
						q=q.substring(1);
					else
						q="~"+q;
						
					boolean result = intermediate(q,s);
				
					String infer = String.valueOf(result);
					writer.write(infer.toUpperCase());
				
					if(i<Queries.size()-1)
						writer.newLine();
                 
					System.out.println(result);
				//	System.out.println("<------------------------------Result------------------------------->");
				//	System.out.println(count);
						
				}
			writer.close(); 		
				
		}
		catch(Exception e){}
	}
	
	
	////////////////////////////////////////////////////////////////////
	static boolean intermediate(String q, String s[]){
		
		count = new ArrayList<Integer>();
		ArrayList<String> query = new ArrayList<String>();
		query.add(q);
		
		ArrayList<String> kbase = new ArrayList<String>();
		kbase.add(q);
		count.add(0);
		
		for(int i=0;i<s.length;i++){
			kbase.add(s[i]);
			count.add(0);
		}
		
		//System.out.println("<----KB--->");
		//System.out.println(kbase);
		
		boolean result1 = solve(query, kbase, 1);	//1 means start Kb checking from 2nd element as first element is ~query
	
		return result1;
	} 
	
	
	/////////////////////////////////////////////////////////////////////	
	static boolean solve(ArrayList<String> query, ArrayList<String> kbase, int j){
		
		kbase =  new ArrayList<String>(kbase);
		query =  new ArrayList<String>(query);
		//System.out.println("KB ->"+kbase);
		ArrayList<String> kb1 = new ArrayList<String>();
		
		String pred= query.get(0);
		String pred1 = getPred(pred);
		String pred2 = changeSignForCompare(pred1);  //~ ancestor ka ancestor kiya
     
	//	System.out.println(pred1);	
     
		for(int i=j;i<kbase.size();i++){

			String knowledge = kbase.get(i);

			String[] temp = knowledge.split(" \\| ");
			if(doesItContainPred(temp,pred2) && count.get(i)<=20 ){         ////// do we have that predicate in that entire clause?
			// this for loop has been added so that it could handle kills8 problem
			for(int r=0;r<temp.length;r++){
	//			System.out.println("valu os r is "+ r);
				if(pred2.equals(getPred(temp[r]))){
						int value= count.get(i)+1;

						count.set(i,value);

						kb1 = new ArrayList<String>(kbase);
					//	kbase.remove(i);


	//					System.out.println("Mathcing "+ knowledge);

						boolean answer = resolve(query, pred, pred2, knowledge, kbase, temp[r]);
					//	System.out.println("Got "+ answer+ "for " + knowledge);
					
						if(answer==false)
							kbase = new ArrayList<String>(kb1);
						else
							return true;	
					
					} //  end of if(pred2.equals(getPred(temp[j])))
							
				} 		// end of for j

				
			}
					
		}  /////end of for loop
		
		return false;
	}
	
	
	//////////////////////////////////////////////////////////////////////////
	static boolean resolve(ArrayList<String> query, String pred, String pred2, String knowledge, ArrayList<String> kbase, String multiple){
		query = new ArrayList<String>(query);
		kbase = new ArrayList<String>(kbase);
		
	//	String z[]= knowledge.split(" \\| ");
	//	System.out.println(multiple);
	//	for(int i=0;i<z.length;i++){
			
			String stmt = getPred(multiple);
			if(pred2.equals(stmt)){
				String arg1[] = getArg(pred);
				String arg2[] = getArg(multiple);
			
				////     We are now unifying variables  /////////
				
					for(int j=0; j<arg1.length; j++){
						//System.out.println(arg1[j]);
						//for next time in loop it changes
						arg1 = getArg(pred);
	//					z = knowledge.split(" \\| ");
						arg2 = getArg(multiple);
						
						if(!(arg1[j].equals(arg2[j]))){
							
								if(isitvar(arg2[j]))
								{
									if(isitConst(arg1[j]))
									{
										knowledge=knowledge.replace(arg2[j],arg1[j]);
										multiple = multiple.replace(arg2[j],arg1[j]);
									}
									else     								/////////////////  This else is redundant - remove if time
									{
										knowledge=knowledge.replace(arg2[j],arg1[j]);
										multiple = multiple.replace(arg2[j],arg1[j]);
									}
								
								}
								else
								{
									if(isitvar(arg1[j]))
									{
										pred = pred.replace(arg1[j],arg2[j]);
									}
									else
										return false;
								}
						
						}	// end of if both are not same							
					} // emd of for j loop							
			//	break;   //once we got matching and we have unified var we dont check for other predicates in knowledge
			}
			
	//	}//end of for  i loop
		
		
		//main work of changing pred and calling solve again
	//	System.out.println(pred);
     
		String dumpling = pred;
		if(pred.contains(" | ")){
			pred = pred.substring(pred.indexOf("|")+2);
		}
		else
			pred="";
				
	
	//	System.out.println(knowledge);
		
		String temp[]=knowledge.split(" \\| ");
				
		String junk="";
		for(int i=0;i<temp.length;i++){

			String stmt1 = getPred(temp[i]);

			String arg3[]= getArg(dumpling);

			String arg4[]= getArg(temp[i]);

			if(!(pred2.equals(stmt1) && Arrays.equals(arg3,arg4)) ){			
				junk += temp[i] + " | ";
			}
		}
		
	//	System.out.println("Junk is " +junk);
		
		if(junk.contains(" | "))
			junk = junk.substring(0,junk.length()-3);
		
		if(junk=="" && pred==""){
			return true;
		}
		else 
			if(pred=="" && junk!=""){
				pred = junk;
			}
			else
				if(pred!="" && junk!=""){
					pred = pred + " | " +junk;
				}
				//else pred = pred + ""; 			//this line is for pred ! = null but junk == null
		
		if(pred.contains(" | "))
		{
			String[] temp1 = pred.split(" \\| ");
			ArrayList<String> query1 = new ArrayList<String>();
			
			for(int i=0;i<temp1.length;i++){
				query1.add(temp1[i]);
			}
			
			LinkedHashSet<String> duplicate = new LinkedHashSet<String>();
			
			duplicate.addAll(query1);
			query1.clear();
			query1.addAll(duplicate);
			
			String newpred="";
			
			for(int i=0;i<query1.size();i++){
					newpred += query1.get(i) + " | ";	
			}
			newpred = newpred.substring(0,newpred.length()-3);
			query.clear();
			query.add(newpred);
		}
		else{
			query.clear();
			query.add(pred);
		}
		
	//	System.out.println(query);
		
			boolean virtue = solve(query, kbase, 0);
			if(virtue==false)
				return false;
			else
				return true;
	
		
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	static String[] getArg(String p){
		String []w = p.substring(p.indexOf("(")+1,p.indexOf(")")).split(",");
		return w;
	}	
		
		
	///////////////////////////////////////////////////////////////////////////
	static boolean doesItContainPred(String[] temp, String p){
		for(int i=0;i<temp.length;i++){
			String stmt = getPred(temp[i]);
			if(p.equals(stmt))
			{	//System.out.println(i);
				return true;
			}
		}
		return false;	
	}
	
	
	///////////////////////////////////////////////////////////////////////
	static String getPred(String stmt){
		String l = stmt.substring(0,stmt.indexOf("("));
		return l;
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	static String changeSignForCompare(String stmt){
		if(stmt.charAt(0)=='~')
			return stmt.substring(1);
		else
			return "~"+stmt; 
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	static String standardize(String s, int n){
		String st[];
		String finalstr="";

		if(s.contains(" | "))
		{
			st = s.split(" \\| ");
		}
		else
		{
			st = s.split(" ");
		}
		
		for(int i=0;i<st.length;i++){
			String subst= st[i].substring(st[i].indexOf("(")+1, st[i].indexOf(")"));
		//	System.out.println(subst);
			String ar[]= subst.split(",");
			String newstr="";
			
			for(int j=0;j<ar.length;j++){
				if(isitvar(ar[j]))
					newstr += ar[j]+n+",";
				else
					newstr +=ar[j]+",";
			}
				newstr = newstr.substring(0,newstr.length()-1);
				
				
				finalstr+=st[i].substring(0,st[i].indexOf("(")) + "(" + newstr + ")" + " | ";
				
			
		}
	//System.out.println(finalstr);
	
		return finalstr.substring(0,finalstr.length()-3);
	} // end of standardise
	
	
	/////////////////////////////////////////////////////////////////////////////////
	static boolean isitvar(String v){
		if(Character.isLowerCase(v.charAt(0)))
			return true;
		else
			return false;
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////
	static boolean isitConst(String v){
		if(Character.isUpperCase(v.charAt(0)))
			return true;
		else
			return false;
		
	}
	
}//end of class