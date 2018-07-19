package com.agileengine;

import java.io.IOException;
import java.util.Arrays;

public class MainCraw {

	private static final String idElement = "make-everything-ok-button";

	public static void main(String[] args) throws IOException {
		try {

			DataForSearch data = ClawerAgente.loadDataforSearch(args[0], idElement);
			Arrays.asList(args).forEach(listOfArgs -> {

				try {
					ClawerAgente.navigatingPage(data, listOfArgs, idElement);
				} catch (IOException e) {

					e.printStackTrace();
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
