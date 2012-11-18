/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skills;

import unit.Unit;

/**
 *
 * @author lohnn
 */
public class Healing extends ActiveSkill {
    @Override
    void effect() {
	targets.add(caster);
	for (Unit target : targets) {
	}
    }

    @Override
    public void setup() {
	name = "Healing";
//	properties.add();
    }
}