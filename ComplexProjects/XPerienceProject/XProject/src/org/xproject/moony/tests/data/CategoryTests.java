package org.xproject.moony.tests.data;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.xproject.moony.XProjectActivity;
import org.xproject.moony.data.DatabaseObject;
import org.xproject.moony.data.contentresolver.XResolver;
import org.xproject.moony.data.objects.category.Category;

import android.database.SQLException;
import android.test.ActivityInstrumentationTestCase2;

public class CategoryTests extends ActivityInstrumentationTestCase2<XProjectActivity> {

	public CategoryTests() {
		super("org.xproject.moony", XProjectActivity.class);
	}

	private XProjectActivity activity;

	protected void setUp() throws Exception {
		super.setUp();

		// Find views
		activity = getActivity();
	}

	public void testInsertCategory() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		List<DatabaseObject> list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		category = (Category) category.save(category, resolver);
		list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	public void testInsertManyCategories() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		List<DatabaseObject> list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		category = (Category) category.save(category, resolver);
		category = new Category("Sala");
		category = (Category) category.save(category, resolver);
		category = new Category("Quarto");
		category = (Category) category.save(category, resolver);
		category = new Category("Dispensa");
		category = (Category) category.save(category, resolver);
		category = new Category("Telhado");
		category = (Category) category.save(category, resolver);
		category = new Category("Garagem");
		category = (Category) category.save(category, resolver);

		list = category.findAll(list, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(6, list.size());
	}

	public void testUpdateCategory() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		List<DatabaseObject> list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		category = (Category) category.save(category, resolver);
		list = category.findAll(list, resolver);

		Assert.assertEquals("Cozinha", ((Category) list.get(0)).getName());

		category.setName("Sala");
		category = (Category) category.save(category, resolver);
		list.clear();
		list = category.findAll(list, resolver);

		Assert.assertEquals("Sala", ((Category) list.get(0)).getName());

		list.clear();
		list = category.findAll(list, resolver);

		Assert.assertEquals(1, list.size());
	}

	public void testUpdateManyCategories() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		List<DatabaseObject> list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		category = (Category) category.save(category, resolver);
		category = new Category("Sala");
		category = (Category) category.save(category, resolver);
		category = new Category("Quarto");
		category = (Category) category.save(category, resolver);
		category = new Category("Dispensa");
		category = (Category) category.save(category, resolver);
		category = new Category("Telhado");
		category = (Category) category.save(category, resolver);
		category = new Category("Garagem");
		category = (Category) category.save(category, resolver);

		List<DatabaseObject> tmpList = list;
		list = category.findAll(list, resolver);
		Assert.assertEquals(tmpList, list);

		Assert.assertEquals("Cozinha", ((Category) list.get(0)).getName());
		Assert.assertEquals("Sala", ((Category) list.get(1)).getName());
		Assert.assertEquals("Quarto", ((Category) list.get(2)).getName());
		Assert.assertEquals("Dispensa", ((Category) list.get(3)).getName());
		Assert.assertEquals("Telhado", ((Category) list.get(4)).getName());
		Assert.assertEquals("Garagem", ((Category) list.get(5)).getName());

		try {
			category = (Category) list.get(0);
			category.setName("Sala");
			category = (Category) category.save(category, resolver);

			fail(); // already exists in db and there cant be repeated
					// categories
		} catch (SQLException exception) {
		}

		category = (Category) list.get(5);
		category.setName("TmpName");
		category = (Category) category.save(category, resolver);

		category = (Category) list.get(4);
		category.setName("Garagem");
		category = (Category) category.save(category, resolver);

		category = (Category) list.get(3);
		category.setName("Telhado");
		category = (Category) category.save(category, resolver);

		category = (Category) list.get(2);
		category.setName("Dispensa");
		category = (Category) category.save(category, resolver);

		category = (Category) list.get(1);
		category.setName("Quarto");
		category = (Category) category.save(category, resolver);

		category = (Category) list.get(0);
		category.setName("Sala");
		category = (Category) category.save(category, resolver);

		category = (Category) list.get(5);
		category.setName("Cozinha");
		category = (Category) category.save(category, resolver);

		list.clear();
		list = category.findAll(list, resolver);

		Assert.assertEquals("Sala", ((Category) list.get(0)).getName());
		Assert.assertEquals("Quarto", ((Category) list.get(1)).getName());
		Assert.assertEquals("Dispensa", ((Category) list.get(2)).getName());
		Assert.assertEquals("Telhado", ((Category) list.get(3)).getName());
		Assert.assertEquals("Garagem", ((Category) list.get(4)).getName());
		Assert.assertEquals("Cozinha", ((Category) list.get(5)).getName());
	}

	public void testRemoveCategory() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha");
		category.removeAll(null, resolver);
		List<DatabaseObject> list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		category = (Category) category.save(category, resolver);
		list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());

		category.remove(category, resolver);
		list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	public void testRemoveManyCategories() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha"), category1, category2, category3, category4, category5;
		category.removeAll(null, resolver);
		List<DatabaseObject> list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		category = (Category) category.save(category, resolver);
		category1 = new Category("Sala");
		category1 = (Category) category1.save(category1, resolver);
		category2 = new Category("Quarto");
		category2 = (Category) category2.save(category2, resolver);
		category3 = new Category("Dispensa");
		category3 = (Category) category3.save(category3, resolver);
		category4 = new Category("Telhado");
		category4 = (Category) category4.save(category4, resolver);
		category5 = new Category("Garagem");
		category5 = (Category) category5.save(category5, resolver);
		list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(6, list.size());

		category.remove(category, resolver);
		category.remove(category1, resolver);
		category.remove(category2, resolver);
		category.remove(category3, resolver);
		category.remove(category4, resolver);
		category.remove(category5, resolver);
		list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	public void testRemoveManyCategoriesAtOnce() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha"), category1, category2, category3, category4, category5;
		category.removeAll(null, resolver);
		List<DatabaseObject> list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		category = (Category) category.save(category, resolver);
		category1 = new Category("Sala");
		category1 = (Category) category1.save(category1, resolver);
		category2 = new Category("Quarto");
		category2 = (Category) category2.save(category2, resolver);
		category3 = new Category("Dispensa");
		category3 = (Category) category3.save(category3, resolver);
		category4 = new Category("Telhado");
		category4 = (Category) category4.save(category4, resolver);
		category5 = new Category("Garagem");
		category5 = (Category) category5.save(category5, resolver);
		list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(6, list.size());

		List<DatabaseObject> listToRemove = new ArrayList<DatabaseObject>();
		listToRemove.add(category);
		listToRemove.add(category1);
		listToRemove.add(category2);
		listToRemove.add(category3);
		listToRemove.add(category5);

		category.removeAll(listToRemove, resolver);
		list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("Telhado", ((Category) list.get(0)).getName());
	}

	public void testRemoveAllCategoriesAtOnce() {
		XResolver resolver = new XResolver(activity);

		Category category = new Category("Cozinha"), category1, category2, category3, category4, category5;
		category.removeAll(null, resolver);
		List<DatabaseObject> list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());

		category = (Category) category.save(category, resolver);
		category1 = new Category("Sala");
		category1 = (Category) category1.save(category1, resolver);
		category2 = new Category("Quarto");
		category2 = (Category) category2.save(category2, resolver);
		category3 = new Category("Dispensa");
		category3 = (Category) category3.save(category3, resolver);
		category4 = new Category("Telhado");
		category4 = (Category) category4.save(category4, resolver);
		category5 = new Category("Garagem");
		category5 = (Category) category5.save(category5, resolver);
		list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(6, list.size());

		category.removeAll(null, resolver);
		list = category.findAll(null, resolver);

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
