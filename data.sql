-- Insert sample issues ONLY IF TABLE EMPTY
INSERT INTO issue (title, description, status, priority, created_at, updated_at)
SELECT 'Sample Issue 1', 'This is sample issue', 'OPEN', 'HIGH', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM issue);
