package com.wagnersantos.cursomc.resources;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	public static String decodificadorNomeEspaco(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> conversorListaInteger(String s) {
		String[] vet = s.split(",");
		List<Integer> lista = new ArrayList<>();
		for (int i = 0; i < vet.length; i++) {
			lista.add(Integer.parseInt(vet[i]));
		}
		return lista;
		// exemplo abaixo do codigo acima feito com lambida
		// return Arrays.asList(s.split(",")).stream().map(x ->
		// Integer.parseInt(x)).collect(Collectors.toList());
	}
}
