-- Paziente demo già presente nella migration V2 sui database nuovi.
-- La clausola evita duplicati se il seed viene adattato o rieseguito manualmente.
INSERT INTO patients (first_name, last_name, birth_date, tax_code)
VALUES ('Mario', 'Rossi', '1985-04-12', 'RSSMRA85D12H501X')
ON CONFLICT (tax_code) DO NOTHING;

-- Crea la cartella clinica del paziente demo solo se non esiste.
INSERT INTO medical_records (patient_id)
SELECT id
FROM patients
WHERE tax_code = 'RSSMRA85D12H501X'
  AND NOT EXISTS (
      SELECT 1
      FROM medical_records mr
      WHERE mr.patient_id = patients.id
  );

INSERT INTO diagnoses (medical_record_id, description)
SELECT mr.id, 'Ipertensione arteriosa lieve'
FROM medical_records mr
JOIN patients p ON p.id = mr.patient_id
WHERE p.tax_code = 'RSSMRA85D12H501X'
  AND NOT EXISTS (
      SELECT 1
      FROM diagnoses d
      WHERE d.medical_record_id = mr.id
        AND d.description = 'Ipertensione arteriosa lieve'
  );

INSERT INTO prescriptions (medical_record_id, medication, dosage, instructions)
SELECT mr.id, 'Farmaco demo', '10 mg', 'Assumere una compressa al giorno dopo colazione'
FROM medical_records mr
JOIN patients p ON p.id = mr.patient_id
WHERE p.tax_code = 'RSSMRA85D12H501X'
  AND NOT EXISTS (
      SELECT 1
      FROM prescriptions pr
      WHERE pr.medical_record_id = mr.id
        AND pr.medication = 'Farmaco demo'
  );

INSERT INTO exam_results (medical_record_id, exam_type, result, performed_at)
SELECT mr.id, 'Esame del sangue', 'Valori nella norma', DATE '2026-09-25'
FROM medical_records mr
JOIN patients p ON p.id = mr.patient_id
WHERE p.tax_code = 'RSSMRA85D12H501X'
  AND NOT EXISTS (
      SELECT 1
      FROM exam_results er
      WHERE er.medical_record_id = mr.id
        AND er.exam_type = 'Esame del sangue'
  );
