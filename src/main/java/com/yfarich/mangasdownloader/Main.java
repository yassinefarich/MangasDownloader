package com.yfarich.mangasdownloader;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
public class Main {

	private static File parametersFile;

	public static File getParametersFile() {
		return parametersFile;
	}

	public static void main(String[] args) {
		Preconditions.checkArgument(args.length > 0, "You need to specify a parameters File ");
		parametersFile = new File(args[0]);

		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		AppStarter application = ctx.getBean(AppStarter.class);
		application.startProcessing();

	}

}
