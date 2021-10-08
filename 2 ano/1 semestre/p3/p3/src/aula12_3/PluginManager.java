package aula12_3;

abstract class PluginManager {

    public static Plugin load(String name) throws Exception {
        Class<?> c = Class.forName(name);
        return (Plugin) c.newInstance();
    }

}