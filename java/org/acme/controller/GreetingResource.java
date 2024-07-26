package org.acme.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.acme.dataaccess.PostgresMapDao;
import org.acme.model.GameMap;

import java.util.List;
import java.util.MissingResourceException;

@Path("/yikes")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        StringBuilder str = new StringBuilder();

        str.append("[");

        try {
            PostgresMapDao dao = new PostgresMapDao();
            List<GameMap> maps = dao.getMap();

            for(GameMap map : maps) {
                str.append(map.asJSON());
                str.append(',');
            }

            removeTrailingComma(str);
            str.append("]");

        } catch (MissingResourceException e) {
            str = new StringBuilder(e.getMessage());
        }

        return str.toString();
    }

    private void removeTrailingComma(StringBuilder b) {
        //Removes only comma at last position
        int position = b.length() - 1;
        if (b.charAt(position) == ',')
            b.deleteCharAt(position);
    }

}
