package com.tutorials.hp.listview_crud;

import java.util.ArrayList;

/**
 * Created by Oclemy for ProgrammingWizards TV Channel and https://www.camposha.info.
 */
public class CRUD {

    private ArrayList<String> names =new ArrayList<>();
    private ArrayList<String> ids =new ArrayList<>();
    private ArrayList<String> totals =new ArrayList<>();

    public void save(String name)
    {
       names.add(name);

    }



    public ArrayList<String> getNames()
    {

        return names;
    }

    public Boolean update(int position,String newName)
    {
       try {
           names.remove(position);
           names.add(position,newName);

           return true;
       }catch (Exception e)
       {
           e.printStackTrace();
          return false;
        }
    }

    public Boolean delete(int position)
    {
        try {
            names.remove(position);

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;

        }
    }
}
