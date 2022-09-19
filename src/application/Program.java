package application;

import model.entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);

		Scanner sc = new Scanner(System.in);

		System.out.println("Entre o caminho do arquivo: ");
		String sourceFileStr = sc.nextLine();

		// Reading File and Creating List

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {

			List<Sale> saleList = new ArrayList<>();

			String item = br.readLine();

			while (item != null) {

				String[] fields = item.split(",");
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);

				saleList.add(new Sale(month, year, seller, items, total));

				item = br.readLine();
			}

			// 5 Highest Average Sales 2016

			Comparator<Sale> comp = (s1, s2) -> {
				return s1.averagePrice().compareTo(s2.averagePrice());
			};

			List<Sale> bestSales = saleList.stream().filter(x -> x.getYear() == 2016).sorted(comp.reversed()).limit(5)
					.collect(Collectors.toList());

			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			for (Sale sale : bestSales) {
				System.out.println(sale.toString());
			}

			// Total Sum of Logan Sales Month 1 or 7

			Double sum = saleList.stream().filter(x -> x.getSeller().equals("Logan"))
					.filter(x -> x.getMonth() == 7 || x.getMonth() == 1).mapToDouble(x -> x.getTotal())
					.reduce(0, (x, y) -> x + y);

			System.out.println();
			System.out.print("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = ");
			System.out.println(sum);

		}

		catch (java.io.IOException e) {
			System.out.println("Erro: " + sourceFileStr + " (O sistema não pôde encontrar o caminho especificado)");
		} 
		
		finally {
			sc.close();
		}

	}

}
