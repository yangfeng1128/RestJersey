package edu.gatech.project3for6310.services;

import java.util.concurrent.LinkedBlockingQueue;

import edu.gatech.project3for6310.Engine.Engine;

public class SimulationService {
	private static SimulationService simulation;
	private static final LinkedBlockingQueue<String> studentModeQueue = new LinkedBlockingQueue<String>();
	private static final LinkedBlockingQueue<String> shadowModeQueue = new LinkedBlockingQueue<String>();
	private Engine studentEngine;
	private Engine shadowEngine;
	
	private SimulationService()
	{		
	}
	
	public static synchronized SimulationService getInstance()
	{
		if (simulation == null)
		{
			simulation = new SimulationService();
		}
		return simulation;
	}
	
	public void addStudentRequest(String requestId)
	{
		studentModeQueue.add(requestId);
	}
	

	public void addShadowRequest(String requestId)
	{
		shadowModeQueue.add(requestId);
	}

}
