package com.googlecode.gwtmvc.poc.client.controller;

import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.googlecode.gwtmvc.client.BrowserEvent;
import com.googlecode.gwtmvc.client.Controller;
import com.googlecode.gwtmvc.client.Event;
import com.googlecode.gwtmvc.client.IView;
import com.googlecode.gwtmvc.client.place.DivWrapperPlacer;
import com.googlecode.gwtmvc.client.place.DomPlacer;
import com.googlecode.gwtmvc.poc.client.model.PocModel;
import com.googlecode.gwtmvc.poc.client.view.PocViewGraphical;
import com.googlecode.gwtmvc.poc.client.view.PocViewNumeric;
import com.googlecode.gwtmvc.poc.client.view.PocViewNumericB;
import com.googlecode.gwtmvc.poc.client.view.PocViewNumericWithMaskable;
import com.googlecode.gwtmvc.poc.client.view.PocViewNumericWithMasker;

public class PocController extends Controller {

	public enum PocAction {
		SHOW_SIMPLE_1, SHOW_COMPLEX_2, SHOW_MASKER, SHOW_MASKABLE, DO_PLUS_A, DO_MINUS_A, DO_REINIT_A, DO_PLUS_B, DO_MINUS_B, DO_REINIT_B, SHOW_URLPARAMS
	}

	protected IView<Integer> pocViewNumeric;
	protected IView<Integer> pocViewNumericB;
	protected IView<Integer> pocViewGraphical;
	protected IView<Integer> pocViewNumericWithMaskable;
	protected IView<Integer> pocViewNumericWithMasker;

	protected PocModel modelA, modelB;

	protected DomPlacer content;

	public PocController() {
		super(PocAction.values());
		modelA = new PocModel();
		modelB = new PocModel();
	}

	@Override
	public void init() {
		Log.debug("Controller init");

		if (pocViewNumeric == null)
			pocViewNumeric = new PocViewNumeric(this, modelA);
		if (pocViewNumericB == null)
			pocViewNumericB = new PocViewNumericB(this, modelB);
		if (pocViewGraphical == null)
			pocViewGraphical = new PocViewGraphical(this, modelA, modelB);

		if (pocViewNumericWithMaskable == null)
			pocViewNumericWithMaskable = new PocViewNumericWithMaskable(this, modelA);
		if (pocViewNumericWithMasker == null)
			pocViewNumericWithMasker = new PocViewNumericWithMasker(this, modelA);

		initModel(modelA);
		initModel(modelB);

		if (content == null) {
			content = new DivWrapperPlacer("content"){
				public void add(IView view) {
					Log.debug(toString() + " add "+ view);
					super.add(view);
				}
			};
		}
	}

	@Override
	public void showHomeView() {

	}

	@Override
	protected void handleEvent(Event event) {
		Log.debug("Controller handleEvent " + event);

		PocAction action = (PocAction) event.getAction();

		switch (action) {
		case SHOW_SIMPLE_1:
			content.clearAndAdd(pocViewNumeric);
			break;
		case SHOW_COMPLEX_2:
			content.clearAndAdd(pocViewNumeric);
			content.add(pocViewNumericB);
			content.add(pocViewGraphical);
			break;
		case SHOW_MASKER:
			content.clearAndAdd(pocViewNumericWithMasker);
			break;
		case SHOW_MASKABLE:
			content.clearAndAdd(pocViewNumericWithMaskable);
			break;
		case DO_PLUS_A:
			modelA.plus((Integer) event.getValue(), event);
			break;
		case DO_PLUS_B:
			modelB.plus((Integer) event.getValue(), event);
			break;
		case DO_MINUS_A:
			modelA.minus((Integer) event.getValue(), event);
			break;
		case DO_MINUS_B:
			modelB.minus((Integer) event.getValue(), event);
			break;
		case DO_REINIT_A:
			updateModel(modelA, 0, event);
			break;
		case DO_REINIT_B:
			updateModel(modelB, 0, event);
			break;
		case SHOW_URLPARAMS:
			content.clearAndAdd(pocViewNumeric);
			Integer modelAParamValue = Integer.valueOf(getUrlParam("modelA"));
			updateModel(modelA, modelAParamValue, event);
			break;
		default:
			Log.debug("Unknown action");
		}
	}

	@Override
	@Deprecated
	protected void renderView(IView view) {

	}

	@Override
	protected Event tryConvertBrowserEventToControllerEvent(BrowserEvent browserEvent) {
		return super.tryConvertBrowserEventToControllerEvent(browserEvent);
	}

	@Override
	public Map<String, String> getUrlParamsMap() {
		return super.getUrlParamsMap();
	}
}