package com.wj.core.repository.activity;

import com.wj.core.entity.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
}
