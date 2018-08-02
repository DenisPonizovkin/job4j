select a.id,a.name, b.id, e.id, t.id from auto as a
inner join (select * from body) b on a.body_id=b.id
inner join (select * from engine) e on a.engine_id = e.id
inner join (select * from transmission) t on a.transmission_id = t.id
------------------------------------------
select b.id from body as b left outer join auto as a on 
a.body_id = b.id and a.id is NULL

select e.id from engine as b left outer join auto as a on 
a.engine_id = e.id and a.id is NULL

select t.id from transmission as b left outer join auto as a on 
a.transmission_id = t.id and a.id is NULL

