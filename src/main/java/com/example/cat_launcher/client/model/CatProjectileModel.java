package com.example.cat_launcher.client.model;

import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Cat;

public class CatProjectileModel<T extends Cat> extends OcelotModel<T> {

	public CatProjectileModel(ModelPart model) {
		super(model);
	}

}
