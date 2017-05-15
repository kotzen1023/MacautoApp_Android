package com.macauto.macautoapp_android.Data;

public class Constants {
    public interface ACTION {
        String GET_START_ALARM_SERVICE_ACTION = "com.macauto.MacautoApp.GetStartAlarmServiceAction";
        String CHECK_EMPLOYEE_EXIST_ACTION = "com.macauto.MacautoApp.CheckEmployeeExist";
        String CHECK_EMPLOYEE_EXIST_COMPLETE = "com.macauto.MacautoApp.CheckEmployeeExistComplete";
        String CHECK_EMPLOYEE_EXIST_FAIL = "com.macauto.MacautoApp.CheckEmployeeExistFail";
        String SOAP_CONNECTION_FAIL = "com.macauto.MacautoApp.SoapConnectionFail";
        String GET_PERSONAL_MEETING_LIST_ACTION = "com.macauto.MacautoApp.GetPersonalMeetingList";
        String GET_PERSONAL_MEETING_LIST_COMPLETE = "com.macauto.MacautoApp.GetPersonalMeetingListComplete";
        String GET_PERSONAL_MEETING_LIST_SORT_COMPLETE = "com.macauto.MacautoApp.GetPersonalMeetingListSortComplete";
        String GET_ALL_MEETING_LIST_ACTION = "com.macauto.MacautoApp.GetAllMeetingList";
        String GET_ALL_MEETING_LIST_COMPLETE = "com.macauto.MacautoApp.GetAllMeetingListComplete";
        String GET_ALL_MEETING_LIST_SORT_COMPLETE = "com.macauto.MacautoApp.GetAllMeetingListSortComplete";
        String GET_PERSONAL_JOB_LIST_ACTION = "com.macauto.MacautoApp.GetJobList";
        String GET_PERSONAL_JOB_LIST_COMPLETE = "com.macauto.MacautoApp.GetJobListComplete";
        String GET_PERSONAL_JOB_LIST_SORT_COMPLETE = "com.macauto.MacautoApp.GetJobListSortComplete";
        String GET_CHATTING_ROSTER_COMPLETE = "com.macauto.MacautoApp.GetChattingListComplete";
        String GET_CHATTING_CONTACT_CHANGE = "com.macauto.MacautoApp.GetChattingContactChange";
        String CHAT_START_SERVICE_ACTION = "com.macauto.MacautoApp.ChatStartServiceAction";
        String CHAT_LOGIN_FAILED = "com.macauto.MacautoApp.ChatLoginFailed";
        String CHAT_SEARCH_FRIENDS_ACTION = "com_macauto.MacautoApp.SearchFriends";
        String CHAT_SEARCH_FRIENDS_COMPLETE = "com.macauto.MacautoApp.SearchFriendsComplete";
        String CHAT_RECEIVE_NEW_MESSAGE = "com.macauto.MacautoApp.NewMessage";
        String CHAT_RECEIVE_NEW_INVITATION = "com.macauto.MacautoApp.NewInvitation";
        String CHAT_RECEIVE_NEW_FILE_INIT = "com.macauto.MacautoApp.ReceiveFileInit";
        String CHAT_RECEIVE_NEW_FILE_COMPLETE = "com.macauto.MacautoApp.ReceiveFileComplete";
        String CHAT_RECEIVE_FILE_UPDATE = "com.macauto.MacautoApp.ReceiveFileUpdate";
        String CHAT_SEND_FILE_UPDATE = "com.macauto.MacautoApp.SendFileUpdate";
        String CHAT_SEND_FILE_COMPLETE = "com.macauto.MacautoApp.SendFileComplete";
        String CHAT_SEND_FILE_FAILED = "com.macauto.MacautoApp.SendFileFailed";
        String CHAT_FILE_RECEIVE_REQUEST = "com.macauto.MacautoApp.ReceiveRequest";
        String CHAT_FILE_RECEIVE_ACCEPT = "com.macauto.MacautoApp.ReceiveAccept";
        String CHAT_FILE_RECEIVE_REJECT = "com.macauto.MacautoApp.ReceiveReject";
        String CHAT_FILE_RECEIVE_COMPLETE = "com.macauto.MacautoApp.ReceiveComplete";
        String CHAT_ADD_ROSTER_ENTRY_TO_GROUP_SUCCESS = "com.macauto.MacautoApp.AddRosterEntryToGroupSuccess";
        String CHAT_ADD_ROSTER_ENTRY_TO_GROUP_FAILED = "com.macauto.MacautoApp.AddRosterEntryToGroupFailed";
        String CHAT_EDIT_GROUP_REMOVE_GROUP_SUCCESS = "com.macauto.MacautoApp.RemoveGroupSuccess";
        String CHAT_REMOVE_ROSTER_ENTRY_FROM_GROUP_SUCCESS = "com.macauto.MacautoApp.RemoveRosterEntryFromGroupSuccess";
        String CHAT_REMOVE_ROSTER_ENTRY_FROM_ROSTER_SUCCESS = "com.macauto.MacautoApp.RemoveRosterEntryFromRosterSuccess";
        String CHAT_MULTIUSER_CHAT_INVITE_ACCEPT_ACTION = "com.macauto.MacautoApp.ChatMultiuserChatInviteAcceptAction";
        String CHAT_MULTIUSER_CHAT_INVITE_DENY_ACTION = "com.macauto.MacautoApp.ChatMultiuserChatInviteDenyAction";
        String CHAT_MULTIUSER_CHAT_BEEN_KICKED_ACTION = "com.macauto.MacautoApp.ChatMultiuserChatBeenKickedAction";
        String CHAT_MULTIUSER_SOMEONE_HAS_JOINED_THIS_CHATROOM_ACTION = "com.macauto.MacautoApp.ChatMultiuserSomeoneHasJoinedThisChatroomAction";
        String CHAT_MULTIUSER_SOMEONE_HAS_LEAVED_THIS_CHATROOM_ACTION = "com.macauto.MacautoApp.ChatMultiuserSomeoneHasLeavedThisChatroomAction";
        String CHAT_NEW_ACTIVITY_MUST_CLOSE = "com.macauto.MacautoApp.ChatNewActivityMustClose";
        String CHAT_ACCOUNT_REGISTER_SUCCESS = "com.macauto.MacautoApp.ChatRegisterSuccess";
        String CHAT_ACCOUNT_REGISTER_NO_RESPONSE = "com.macauto.MacautoApp.ChatRegisterNoResponse";
        String CHAT_ACCOUNT_REGISTER_CONFLICT = "com.macauto.MacautoApp.ChatRegisterConflict";
        String CHAT_ACCOUNT_REGISTER_NOT_ACCEPTABLE = "com.macauto.MacautoApp.ChatRegisterNotAcceptable";
        String CHAT_ACCOUNT_REGISTER_ERROR_UNKNOWN = "com.macauto.MacautoApp.ChatRegisterErrorUnknown";
        String CHAT_CHANGE_PASSWORD_SUCCESS = "com.macauto.MacautoApp.ChatChangePasswordSuccess";

        //Command
        String CHAT_CONNECTION_RELOAD_CONTACT = "com.macauto.MacautoApp.ChatConnectionReloadContact";
        String CHAT_CONNECTION_RELOAD_CONTACT_COMPLETE = "com.macauto.MacautoApp.ChatConnectionReloadContactComplete";

        //mqtt
        String MQTT_START_SERVICE_ACTION = "com.macauto.MacautoApp.MqttStartServiceAction";
        String MQTT_SUBSCRIBE_CHANGE = "com.macauto.MacautoApp.MqttSubscribeChange";
        String MQTT_GET_HISTORY_CONNECTION_INFO = "com.macauto.MacautoApp.MqttGetHistoryConnectionInfo";
        String MQTT_GET_HISTORY_MATCH_MESSAGE = "com.macauto.MacautoApp.MqttGetHistoryMatchMessage";
        String MQTT_CLEAR_HISTORY = "com.macauto.MacautoApp.MqttClearHistory";
    }

    public interface SUBJECT {
        String SUBJECT_IT = "[Macauto IT]";
        String SUBJECT_PRODUCT = "[Macauto Production]";
        String SUBJECT_SALES = "[Macauto Sales]";
    }
}
