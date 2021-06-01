import java.util.Comparator;
import java.util.Iterator;

import arboles.Monticulo;
import tablasHash.HashMapArray;
import tablasHash.MultiHash;

public class Main {

	public static void main(String[] args) {

//		
//		Monticulo<Integer> m = new Monticulo<Integer>();
//		
//		m.add(41);
//		m.add(2);		
//		m.add(26);
//		m.add(7);
//		m.add(31);
//		m.add(5);
//		m.add(4);
//		m.add(88);
//		m.add(10);
//		m.add(9);
//		
//		//----------------------------------
//		System.out.print("Recorrido por niveles\n");
//		Iterator<Integer> it = m.iterator();
//		while (it.hasNext()) {
//			System.out.print(it.next() + " ");
//		}
//		
//		//----------------------------------
//		
//		System.out.print("\nSalida cola de prioridad\n");
//		while (!m.isEmpty()) {
//			System.out.print(m.removeFirst() + " ");
//		}
//		
//		
//		
//		Monticulo<String> ms = new Monticulo<String>();
//		ms.add("a");
//		ms.add("b");		
//		ms.add("c");
//		ms.add("z");
//		ms.add("y");
//		ms.add("x");
//		ms.add("xz");
//		ms.add("xy");
//		ms.add("cc");
//
//		//----------------------------------
//		System.out.print("\nRecorrido por niveles\n");
//		Iterator<String> its = ms.iterator();
//		while (its.hasNext()) {
//			System.out.print(its.next() + " ");
//		}
//		
//		//----------------------------------
//		
//		System.out.print("\nSalida cola de prioridad\n");
//		while (!ms.isEmpty()) {
//			System.out.print(ms.removeFirst() + " ");
//		}
//		
//	
//		
//		Monticulo<String> mc = new Monticulo<String>(new Comparator<String>()
//		{
//			@Override
//			public int compare(String arg0, String arg1) {
//				
//				return arg0.length() - arg1.length();
////				
////				if (arg0.length() > arg1.length())
////					return arg0;
////				else if (arg0.length() < arg1.length())
////					return -1;
////				else		
////					return 0;
//			}
//         
//        });
//
//		
//
//		mc.add("aaa");
//		mc.add("bbbbbbb");		
//		mc.add("cccc");
//		mc.add("z");
//		mc.add("y");
//		mc.add("x");
//		mc.add("xz");
//		mc.add("xy");
//		mc.add("cc");
//		
//
//		//----------------------------------
//		System.out.print("\nRecorrido por niveles\n");
//		Iterator<String> itc = mc.iterator();
//		while (itc.hasNext()) {
//			System.out.print(itc.next() + " ");
//		}
//		
//		//----------------------------------
//		
//		System.out.print("\nSalida cola de prioridad\n");
//		while (!mc.isEmpty()) {
//			System.out.print(mc.removeFirst() + " ");
//		}
//		
//		
//		mc = new Monticulo<String>(new Comparator<String>()
//		{
//			@Override
//			public int compare(String arg0, String arg1) {
//				
//				return arg0.length() - arg1.length();
////				
////				if (arg0.length() > arg1.length())
////					return arg0;
////				else if (arg0.length() < arg1.length())
////					return -1;
////				else		
////					return 0;
//			}
//         
//        });
//
//		
//
//		mc.add("aaa");
//		mc.add("bbbbbbb");		
//		mc.add("cccc");
//		mc.add("z");
//		mc.add("y");
//		mc.add("x");
//		mc.add("xz");
//		mc.add("xy");
//		mc.add("xy");
//		mc.add("cc");
//		mc.add("cc");
//		mc.add("cc");
//		
//
//		//----------------------------------
//		System.out.print("\nRecorrido por niveles\n");
//		itc = mc.iterator();
//		while (itc.hasNext()) {
//			System.out.print(itc.next() + " ");
//		}
//		
//		//----------------------------------
//		
//		System.out.print("\nSalida cola de prioridad\n");
//		while (!mc.isEmpty()) {
//			System.out.print(mc.removeFirst() + " ");
//		}
//		
		
		MultiHash<Integer,Integer> h = new MultiHash<Integer,Integer>("COCI");
		
//		for (int i=0; i<30; i++) {
//			h.put(i,i);
//		}
		
		
//		h.put(0,0);
//		h.put(1,1);
//		h.put(4,4);
//		h.put(10,10);
//		h.put(2,2);
//		h.put(20,20);
//		h.put(16,16);
//		
//		int get10 = h.get(10);
//		int get20 = h.get(20);
//		
//		int rem0 = h.remove(0);
//		int rem2 = h.remove(2);

		
		h.put(40,40);
		h.put(19,19);
		h.put(21,21);
		h.put(52,52);
		h.put(46,46);
		h.put(33,33);
		h.put(24,24);
		h.put(0,0);
		
		
		

		


		
		//int get5 = h.get(5);
		
		
		HashMapArray<String,Integer> s = new HashMapArray<String,Integer>(5);
		int size = s.size();

		s.put("uno", 1);
		size = s.size();

		s.put("diez", 10);
		s.put("cinco", 5);
		s.put("seis", 6);
		s.put("once", 11);
		s.put("sesentayseis", 66);
		size = s.size();
		String ss = s.toString();
		
		
		int rem5 = s.remove("cinco");
		s.remove("diez");
		size = s.size();
		
		int get11=s.get("once");
		
		
		int a = 0;

//		
		
	}

}
