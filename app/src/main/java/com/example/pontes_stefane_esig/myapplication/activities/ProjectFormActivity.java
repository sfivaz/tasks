package com.example.pontes_stefane_esig.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pontes_stefane_esig.myapplication.R;
import com.example.pontes_stefane_esig.myapplication.daos.ProjectDAO;
import com.example.pontes_stefane_esig.myapplication.helpers.ProjectFormHelper;
import com.example.pontes_stefane_esig.myapplication.models.Project;

public class ProjectFormActivity extends AppCompatActivity {

    private ProjectFormHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_form);

        helper = new ProjectFormHelper(this);

        Intent intent = getIntent();
        Project project = (Project) intent.getSerializableExtra("project");
        if (project != null)
            helper.setProject(project);
    }

    public void projectSubmit(View view) {
        Project project = helper.getProject();

        ProjectDAO dao = new ProjectDAO(this);
        if (project.getId() != 0)
            dao.update(project);
        else
            dao.insert(project);
        dao.close();

        Toast.makeText(this, project.toString(), Toast.LENGTH_SHORT).show();
        goToHomePage();
    }

    void goToHomePage() {
        Intent home = new Intent(this, HomeActivity.class);
        startActivity(home);
    }
}