package org.spartan.model.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.spartan.cdi.scope.service.Service;
import org.spartan.cdi.util.ReflectionUtil;
import org.spartan.model.entity.DynamicEntity;
import org.spartan.model.entity.player.Player;

/**
 * @author brock
 */
@Service
public class DefaultRealm implements Realm {

	/**
	 * The collection of entities
	 */
	private final Map<Class<? extends DynamicEntity>, Set<? extends DynamicEntity>> entities = new HashMap<>();

	/**
	 * the index pool
	 */
	private final IndexObjectPool pool = new IndexObjectPool(64);

	/**
	 * hello
	 */
	public DefaultRealm() {
		entities.put(Player.class, new HashSet<>());
	}

	@Override
	public <E extends DynamicEntity> Set<E> get(Class<E> type) {
		return ReflectionUtil.cast(entities.get(type));
	}

	@Override
	public <E extends DynamicEntity> void register(E entity) {
		entities.get(entity.getClass()).add(ReflectionUtil.cast(entity));
		entity.setId(pool.next());
	}

	@Override
	public <E extends DynamicEntity> void unregister(E entity) {
		entities.get(entity.getClass()).remove(ReflectionUtil.cast(entity));
		pool.push(entity.getId());
	}
	
	@Override
	public boolean available() {
		return pool.available();
	}

}
