package pl.boxly.chatbot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.boxly.chatbot.models.primitive.TextResponseModel;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatFuelResponseModel {
    List<TextResponseModel> text;
}
