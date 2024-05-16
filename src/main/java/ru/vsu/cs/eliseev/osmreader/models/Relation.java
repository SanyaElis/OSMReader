package ru.vsu.cs.eliseev.osmreader.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Relations")
@TypeAlias("Relation")
@Getter
@Setter
public class Relation extends ElementOnMap {

    private final List<Member> members;//todo переделать

    public Relation(String id) {
        super(id);
        members = new ArrayList<>();
    }

    public void addMember(String role, ElementOnMap element) {
        if (element == null) {
            throw new NullPointerException("Element can't be null");
        }

        members.add(new Member(role, element));
    }

    public void removeMember(ElementOnMap e) {
        members.removeIf(member -> e == member.elementOnMap);
    }

    public String getMemberRole(ElementOnMap e) {
        for (Relation.Member member : members) {
            if (member.elementOnMap == e)
                return member.role;
        }
        throw new RuntimeException("Element " + e.getId() + " not found");
    }

    private static class Member {
        private final String role;
        private final ElementOnMap elementOnMap;

        public Member(String role, ElementOnMap elementOnMap) {
            this.role = role;
            this.elementOnMap = elementOnMap;
        }
    }
}


