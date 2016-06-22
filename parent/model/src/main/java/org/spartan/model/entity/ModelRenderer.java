package org.spartan.model.entity;

import org.spartan.model.entity.sync.render.Render;

public interface ModelRenderer {

	/**
	 * 
	 * @param model
	 * @return
	 */
	Render render(Model model);

}
