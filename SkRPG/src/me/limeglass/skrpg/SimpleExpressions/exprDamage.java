package me.limeglass.skrpg.SimpleExpressions;

import org.bukkit.event.Event;

import com.sucy.skill.api.event.SkillDamageEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.Kleenean;

public class exprDamage extends SimpleExpression<Double> {
	
	@Override
	public boolean init(final Expression<?>[] vars, final int matchedPattern, final Kleenean isDelayed, final ParseResult parser) {
		if (!ScriptLoader.isCurrentEvent(SkillDamageEvent.class)) {
			Skript.error("Cannot use 'SkillAPI used skill' outside of a Skill event", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		return true;
	}
	
	@Override
	protected Double[] get(final Event e) {
		return new Double[] {getDamage(e)};
	}
	
	private static Double getDamage(final Event e) {
		if (e == null)
			return null;
		if (e instanceof SkillDamageEvent) {
			
			final Object o = ((SkillDamageEvent) e).getDamage();
			
			return (Double) o;
		}
		return null;
	}
	
	@Override
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}
	
	@Override
	public String toString(final Event e, final boolean debug) {
		if (e == null)
			return "SkillAPI damage";
		return Classes.getDebugMessage(getSingle(e));
	}
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
}