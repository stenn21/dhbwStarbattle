package com.starbattle.client.views.lobby.friends;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.starbattle.client.layout.DesignButton;
import com.starbattle.client.layout.VerticalLayout;
import com.starbattle.client.layout.ViewModel;
import com.starbattle.client.resource.ResourceLoader;

public class FriendList extends ViewModel {

	private DesignButton showHide;
	private JPanel content = new JPanel();
	private String name;
	private FriendActionListener friendActionListener;
	private ArrayList<FriendRelation> relations = new ArrayList<FriendRelation>();
	private List<FriendRelationView> relationViews = new ArrayList<FriendRelationView>();

	public FriendList(String title, String icon, FriendActionListener friendActionListener) {
		this.friendActionListener = friendActionListener;
		this.name = title;
		showHide = new DesignButton(title, ResourceLoader.loadIcon(icon));
		showHide.setButtonStyle(1);
		initLayout();
	}

	private void initLayout() {
		view.setLayout(new BorderLayout(0, 0));
		view.setOpaque(false);
		showHide.setPreferredSize(new Dimension(280, 30));
		showHide.setHorizontalAlignment(SwingConstants.LEFT);

		view.add(showHide, BorderLayout.NORTH);
		content.setOpaque(false);
		view.add(content, BorderLayout.CENTER);
		content.setLayout(new VerticalLayout());
		content.setVisible(false);

		// change visibility of content on click
		showHide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean visible = content.isVisible();
				content.setVisible(!visible);
			}
		});
	}

	private void update() {
		showHide.setText(name + " (" + content.getComponentCount() + ")");
		content.revalidate();
		this.view.repaint();
	}

	public void initList() {
		content.removeAll();
		relationViews.clear();
		relations.clear();
		update();
	}

	public void addRelation(FriendRelation relation) {
		FriendRelationView view = new FriendRelationView(relation, friendActionListener);
		content.add(view.getView());
		relationViews.add(view);
		relations.add(relation);
		update();
	}

	public ArrayList<FriendRelation> getRelations() {
		return relations;
	}

	public void deleteRelation(String name) {
		for (int i = 0; i < relations.size(); i++) {
			FriendRelation relation = relations.get(i);
			if (relation.getName().equals(name)) {
				relations.remove(i);
				relationViews.remove(i);
				// remove view component
				content.remove(i);
				update();
				break;
			}
		}

	}

	public FriendRelationView getView(String name) {
		for (int i = 0; i < relations.size(); i++) {
			FriendRelation relation = relations.get(i);
			if (relation.getName().equals(name)) {
				return relationViews.get(i);
			}
		}
		return null;
	}

	public JPanel getContent() {
		return content;
	}
}
