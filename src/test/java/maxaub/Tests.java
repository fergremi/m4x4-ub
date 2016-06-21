package maxaub;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Tests {
	@Test
	public void multiplicationOfZeroIntegersShouldReturnZero() {
		final int numAsignaturas = 12;

		final int malos = 580;
		final int regulares = 31;
		final int buenos = 100;

		double total = malos + regulares + buenos;
		System.out.println("total: " + total);

		double malo = numAsignaturas * (malos / total);
		double regular = numAsignaturas * (regulares / total);
		double bueno = numAsignaturas * (buenos / total);

		System.out.println("malo: " + malo + " -> " + Math.round(malo));
		System.out.println("regular: " + regular + " -> " + Math.round(regular));
		System.out.println("bueno: " + bueno + " -> " + Math.round(bueno));

		bueno = Math.round(bueno);
		regular = Math.round(regular);
		malo = Math.round(malo);

		int res = (int)(malo + regular + bueno);
		System.out.println("total asignaturas: " + res);

		if (numAsignaturas != res) {
			int op = 3;

			if (op == 1) {
				/* Random */
				int random = ThreadLocalRandom.current().nextInt(1, 3 + 1);
				System.out.println("random: " + random);
				switch (random) {
				case 1:
					bueno--;
					break;
				case 2:
					regular--;
					break;
				case 3:
					malo--;
					break;
				}
			} else if (op == 2) {
				/* MAX */
				if ((bueno > regular) && (bueno > malo)) {
					bueno--;
				}else if ((regular > bueno) && (regular > malo)) {
					regular--;
				}else if ((malo > bueno) && (malo > regular)) {
					malo--;
				}
			} else if (op == 3) {
				/* Termino medio */
				List<Double> list = new ArrayList<Double>();
				list.add(bueno);
				list.add(regular);
				list.add(malo);
				Collections.sort(list);
				if (list.get(1) == bueno) {
					bueno--;
				} else if (list.get(1) == regular) {
					regular--;
				} else if (list.get(1) == malo) {
					malo--;
				}
			}
			
			System.out.println("malo: " + malo);
			System.out.println("regular: " + regular);
			System.out.println("bueno: " + bueno);

			res = (int)(malo + regular + bueno);
			System.out.println("new total asignaturas: " + res);
		}
		assertEquals(numAsignaturas, res);
	}
}
