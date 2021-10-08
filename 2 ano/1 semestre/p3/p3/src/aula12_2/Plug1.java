package aula12_2;

import aula12_2.IPlugin;

public class Plug1 implements IPlugin {
    @Override
    public void fazQualquerCoisa() {
        System.out.println("plug1");
    }
}