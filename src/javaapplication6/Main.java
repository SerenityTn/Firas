package javaapplication6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MASTER
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String line = null;
		List<String[]> list = new ArrayList<String[]>();
		BufferedReader reader = new BufferedReader(new FileReader("C:/test2.txt"));
		while ((line = reader.readLine()) != null) {
			list.add(getArray(line));
		}
		reader.close();
		int i = 0;
		for (String[] stringArr : list) {
			for (String str : stringArr) {
				System.out.print(str + " ");

			}
			System.out.println("");
			i++;
		}
		MST t = new MST(i);
		int[][] graph = new int[i][i];
		int a = 0, b = 0;
		for (String[] stringArr : list) {
			for (String str : stringArr) {
				graph[a][b] = Integer.valueOf(str);
				b++;

			}
			a++;
			b = 0;
		}

		for (int k = 0; k < i; k++) {
			for (int m = 0; m < i; m++) {
				System.out.print(graph[k][m]);
				System.out.print(" ");
			}

			System.out.println("");
		}
		t.primMST(graph);
	}

	private static String[] getArray(String s) {
		String[] array = s.split("\\s");
		return array;
	}

}