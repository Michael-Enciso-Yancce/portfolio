ALTER TABLE skills ADD COLUMN proficiency_level_id BIGINT;

ALTER TABLE skills 
ADD CONSTRAINT fk_skills_proficiency_level 
FOREIGN KEY (proficiency_level_id) 
REFERENCES proficiency_levels (id);
