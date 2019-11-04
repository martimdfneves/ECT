package aula12_2;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

abstract class PluginManager{
	public static IPlugin load(String name)
		throws Exception {
		Class<?> c = Class.forName(name);
		return (IPlugin) c.newInstance();
	}
}

public class Plugin {
	
	public static void main(String args[]) throws Exception {
		File proxyList = new File("src/aula12/e2/plugins");
		ArrayList<IPlugin> plgs = new ArrayList<>();

		for (String f : proxyList.list()) {
			try {
				plgs.add(PluginManager.load("aula12.e2.plugins." + f.substring(0, f.lastIndexOf('.'))));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		plgs.parallelStream().forEach(IPlugin::fazQualquerCoisa);
		plgs.stream().forEach(IPlugin::fazQualquerCoisa);
	}
}