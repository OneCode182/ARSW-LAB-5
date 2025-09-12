package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hcadavid
 */
/**
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    BlueprintsPersistence bpp = null;

    @Autowired
    @Qualifier("redundancyFilter") // Cambiar a "subsamplingFilter"
    BlueprintFilter blueprintFilter;

    public void addNewBlueprint(Blueprint bp) {
        try {
            bpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Blueprint> getAllBlueprints() {
        try {
            Set<Blueprint> blueprints = bpp.getBlueprints();
            Set<Blueprint> filteredBlueprints = new HashSet<>();

            for (Blueprint bp : blueprints) {
                filteredBlueprints.add(blueprintFilter.filter(bp));
            }

            return filteredBlueprints;
        } catch (BlueprintNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint blueprint = bpp.getBlueprint(author, name);
        return blueprintFilter.filter(blueprint);
    }

    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = bpp.getBlueprintsByAuthor(author);
        Set<Blueprint> filteredBlueprints = new HashSet<>();

        for (Blueprint bp : blueprints) {
            filteredBlueprints.add(blueprintFilter.filter(bp));
        }

        return filteredBlueprints;
    }
}