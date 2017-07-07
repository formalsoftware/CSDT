package com.formallab.simulink.mdl;


public class MdlModel extends MdlAbstractSystemOwner {

    public MdlModel(String name) {
        this(name, new MdlSystem(name));
    }

    public MdlModel(String name, MdlSystem system) {
        super("Model", name);
        this.system = system;
    }

}