package io.ucb.rafael.bluefood.infrastructure.web.controller;

import org.springframework.ui.Model;

public class ControllerHelper {

	public static void funcSetEditMode(Model model, boolean isEdit) {
		model.addAttribute("editMode", isEdit);
	}
}
