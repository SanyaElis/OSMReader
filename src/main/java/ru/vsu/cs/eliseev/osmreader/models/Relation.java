package ru.vsu.cs.eliseev.osmreader.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "Relations")
@TypeAlias("Relation")
@Getter
@Setter
public class Relation extends ElementOnMap {

    private List<Member> members;

    public Relation(String id) {
        super(id);
        members = new ArrayList<>();
    }

    public void addMember(String role, String type, String refElement) {
        if (refElement == null) {
            throw new NullPointerException("Element can't be null");
        }

        members.add(new Member(role, type, refElement));
    }

    public void removeMember(String ref) {
        members.removeIf(member -> Objects.equals(ref, member.refMember));
    }

    public String memberType(String ref) {
        for (Relation.Member member : members) {
            if (Objects.equals(member.refMember, ref))
                return member.type;
        }
        throw new RuntimeException("Element " + ref + " not found");
    }

    public String getMemberRole(String ref) {
        for (Relation.Member member : members) {
            if (Objects.equals(member.refMember, ref))
                return member.role;
        }
        throw new RuntimeException("Element " + ref + " not found");
    }

    public record Member(String role, String type, String refMember) {
    }
}


