-- company name for each person
select p.name, c.name from person as p inner join company as c on p.company_id = c.id

-- names of all persons that are NOT in the company with id = 5
select distinct(name) from person where id not in (
select p.id from person as p
inner join company as c on p.company_id = c.id
where c.id = 5)

-- names of all persons that are NOT in the company with id = 5
select distinct(p.name) from person as p
left outer join (select * from company where id = 5) as c on p.company_id = c.id where c.id is NULL

-- Select the name of the company with the maximum number of persons + number of persons in this company 
select rslt.name, rslt.cnt from
((select
		c.name, p1.company_id as cmp_id, count(p1.company_id) as cnt
		from
			person as p1
		inner join
			company as c
		on p1.company_id = c.id

		inner join
			person as p2
		on
			p1.id != p2.id and p1.company_id = p2.company_id

		group by p1.company_id, c.name)
	order by cnt desc limit 1) as rslt
