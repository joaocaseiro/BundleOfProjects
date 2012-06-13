package org.xproject.moony.tests.data;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.xproject.moony.XProjectActivity;
import org.xproject.moony.data.DatabaseObject;
import org.xproject.moony.data.contentresolver.XResolver;
import org.xproject.moony.data.objects.category.Category;
import org.xproject.moony.data.objects.task.Task;

import android.test.ActivityInstrumentationTestCase2;

public class TaskTests extends ActivityInstrumentationTestCase2<XProjectActivity> {

	public TaskTests() {
		super("org.xproject.moony", XProjectActivity.class);
	}

	private XProjectActivity activity;

	protected void setUp() throws Exception {
		super.setUp();

		// Find views
		activity = getActivity();
	}

	public void testInsertTask() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		category.save(category, resolver);
		
		Task task = new Task("Lavar a loica", 200, category);
		task.removeAll(null, resolver);
		List<DatabaseObject> list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		task = (Task) task.save(task, resolver);
		list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	public void testInsertManyTasks() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		category.save(category, resolver);

		Task task = new Task("Lavar a loica", 200, category);
		task.removeAll(null, resolver);
		List<DatabaseObject> list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		task = (Task) task.save(task, resolver);
		task = new Task("Dar comida ao gato", 30, category);
		task = (Task) task.save(task, resolver);
		task = new Task("Preparar massa com carne", 300, category);
		task = (Task) task.save(task, resolver);
		task = new Task("Preparar batatas fritas com ovo", 300, category);
		task = (Task) task.save(task, resolver);
		task = new Task("Preparar bacalhau com natas", 450, category);
		task = (Task) task.save(task, resolver);
		task = new Task("Preparar arroz de frango", 250, category);
		task = (Task) task.save(task, resolver);

		list = task.findAll(list, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(6, list.size());
	}

	public void testUpdateTask() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		category.save(category, resolver);
		
		Task task = new Task("Lavar a loica", 200, category);
		task.removeAll(null, resolver);
		List<DatabaseObject> list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		task = (Task) task.save(task, resolver);
		list = task.findAll(list, resolver);

		Assert.assertEquals("Lavar a loica", ((Task) list.get(0)).getName());
		Assert.assertEquals(200, ((Task) list.get(0)).getValue());

		task.setName("Levar o lixo a rua");
		task.setValue(100);
		task = (Task) task.save(task, resolver);
		list.clear();
		list = task.findAll(list, resolver);

		Assert.assertEquals("Levar o lixo a rua", ((Task) list.get(0)).getName());
		Assert.assertEquals(100, ((Task) list.get(0)).getValue());

		list.clear();
		list = task.findAll(list, resolver);

		Assert.assertEquals(1, list.size());
	}

	public void testUpdateManyTasks() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		category.save(category, resolver);
		
		Task task = new Task("Lavar a loica", 200, category);
		task.removeAll(null, resolver);
		List<DatabaseObject> list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		task = (Task) task.save(task, resolver);
		task = new Task("Dar comida ao gato", 30, category);
		task = (Task) task.save(task, resolver);
		task = new Task("Preparar massa com carne", 300, category);
		task = (Task) task.save(task, resolver);
		task = new Task("Preparar batatas fritas com ovo", 300, category);
		task = (Task) task.save(task, resolver);
		task = new Task("Preparar bacalhau com natas", 450, category);
		task = (Task) task.save(task, resolver);
		task = new Task("Preparar arroz de frango", 250, category);
		task = (Task) task.save(task, resolver);

		List<DatabaseObject> tmpList = list;
		list = task.findAll(list, resolver);
		Assert.assertEquals(tmpList, list);

		Assert.assertEquals("Lavar a loica", ((Task) list.get(0)).getName());
		Assert.assertEquals(200, ((Task) list.get(0)).getValue());
		Assert.assertEquals("Dar comida ao gato", ((Task) list.get(1)).getName());
		Assert.assertEquals(30, ((Task) list.get(1)).getValue());
		Assert.assertEquals("Preparar massa com carne", ((Task) list.get(2)).getName());
		Assert.assertEquals(300, ((Task) list.get(2)).getValue());
		Assert.assertEquals("Preparar batatas fritas com ovo", ((Task) list.get(3)).getName());
		Assert.assertEquals(300, ((Task) list.get(3)).getValue());
		Assert.assertEquals("Preparar bacalhau com natas", ((Task) list.get(4)).getName());
		Assert.assertEquals(450, ((Task) list.get(4)).getValue());
		Assert.assertEquals("Preparar arroz de frango", ((Task) list.get(5)).getName());
		Assert.assertEquals(250, ((Task) list.get(5)).getValue());

		task = (Task) list.get(0);
		task.setName("Lavar a loica");
		task.setValue(200);
		task = (Task) task.save(task, resolver);
		
		task = (Task) list.get(1);
		task.setName("Dar comida ao gato");
		task.setValue(30);
		task = (Task) task.save(task, resolver);

		task = (Task) list.get(2);
		task.setName("Preparar massa com carne");
		task.setValue(300);
		task = (Task) task.save(task, resolver);
		
		task = (Task) list.get(3);
		task.setName("Preparar batatas fritas com ovo");
		task.setValue(300);
		task = (Task) task.save(task, resolver);
		
		task = (Task) list.get(4);
		task.setName("Preparar bacalhau com natas");
		task.setValue(450);
		task = (Task) task.save(task, resolver);
		
		task = (Task) list.get(5);
		task.setName("Preparar arroz de frango");
		task.setValue(250);
		task = (Task) task.save(task, resolver);

		list.clear();
		list = task.findAll(list, resolver);

		Assert.assertEquals("Lavar a loica", ((Task) list.get(0)).getName());
		Assert.assertEquals(200, ((Task) list.get(0)).getValue());
		Assert.assertEquals("Dar comida ao gato", ((Task) list.get(1)).getName());
		Assert.assertEquals(30, ((Task) list.get(1)).getValue());
		Assert.assertEquals("Preparar massa com carne", ((Task) list.get(2)).getName());
		Assert.assertEquals(300, ((Task) list.get(2)).getValue());
		Assert.assertEquals("Preparar batatas fritas com ovo", ((Task) list.get(3)).getName());
		Assert.assertEquals(300, ((Task) list.get(3)).getValue());
		Assert.assertEquals("Preparar bacalhau com natas", ((Task) list.get(4)).getName());
		Assert.assertEquals(450, ((Task) list.get(4)).getValue());
		Assert.assertEquals("Preparar arroz de frango", ((Task) list.get(5)).getName());
		Assert.assertEquals(250, ((Task) list.get(5)).getValue());
	}

	public void testRemoveTask() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		category.save(category, resolver);
		
		Task task = new Task("Lavar a loica", 200, category);
		task.removeAll(null, resolver);
		List<DatabaseObject> list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		task = (Task) task.save(task, resolver);
		list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());

		task.remove(task, resolver);
		list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	public void testRemoveManyTasks() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		category.save(category, resolver);
		
		Task task = new Task("Lavar a loica", 200, category), task1, task2, task3, task4, task5;
		task.removeAll(null, resolver);
		List<DatabaseObject> list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		task = (Task) task.save(task, resolver);
		task1 = new Task("Dar comida ao gato", 30, category);
		task1 = (Task) task1.save(task1, resolver);
		task2 = new Task("Preparar massa com carne", 300, category);
		task2 = (Task) task2.save(task2, resolver);
		task3 = new Task("Preparar batatas fritas com ovo", 300, category);
		task3 = (Task) task3.save(task3, resolver);
		task4 = new Task("Preparar bacalhau com natas", 450, category);
		task4 = (Task) task4.save(task4, resolver);
		task5 = new Task("Preparar arroz de frango", 250, category);
		task5 = (Task) task5.save(task5, resolver);
		list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(6, list.size());

		task.remove(task, resolver);
		task1.remove(task1, resolver);
		task2.remove(task2, resolver);
		task3.remove(task3, resolver);
		task4.remove(task4, resolver);
		task5.remove(task5, resolver);
		list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	public void testRemoveManyTasksAtOnce() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		category.save(category, resolver);
		
		Task task = new Task("Lavar a loica", 200, category), task1, task2, task3, task4, task5;
		task.removeAll(null, resolver);
		List<DatabaseObject> list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		task = (Task) task.save(task, resolver);
		task1 = new Task("Dar comida ao gato", 30, category);
		task1 = (Task) task1.save(task1, resolver);
		task2 = new Task("Preparar massa com carne", 300, category);
		task2 = (Task) task2.save(task2, resolver);
		task3 = new Task("Preparar batatas fritas com ovo", 300, category);
		task3 = (Task) task3.save(task3, resolver);
		task4 = new Task("Preparar bacalhau com natas", 450, category);
		task4 = (Task) task4.save(task4, resolver);
		task5 = new Task("Preparar arroz de frango", 250, category);
		task5 = (Task) task5.save(task5, resolver);
		list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(6, list.size());

		List<DatabaseObject> listToRemove = new ArrayList<DatabaseObject>();
		listToRemove.add(task);
		listToRemove.add(task1);
		listToRemove.add(task2);
		listToRemove.add(task3);
		listToRemove.add(task5);

		task.removeAll(listToRemove, resolver);
		list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("Preparar bacalhau com natas", ((Task) list.get(0)).getName());
		Assert.assertEquals(450, ((Task) list.get(0)).getValue());
	}

	public void testRemoveAllCategoriesAtOnce() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		category.save(category, resolver);
		
		Task task = new Task("Lavar a loica", 200, category), task1, task2, task3, task4, task5;
		task.removeAll(null, resolver);
		List<DatabaseObject> list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		task = (Task) task.save(task, resolver);
		task1 = new Task("Dar comida ao gato", 30, category);
		task1 = (Task) task1.save(task1, resolver);
		task2 = new Task("Preparar massa com carne", 300, category);
		task2 = (Task) task2.save(task2, resolver);
		task3 = new Task("Preparar batatas fritas com ovo", 300, category);
		task3 = (Task) task3.save(task3, resolver);
		task4 = new Task("Preparar bacalhau com natas", 450, category);
		task4 = (Task) task4.save(task4, resolver);
		task5 = new Task("Preparar arroz de frango", 250, category);
		task5 = (Task) task5.save(task5, resolver);
		list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(6, list.size());

		task.removeAll(null, resolver);
		list = task.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
}