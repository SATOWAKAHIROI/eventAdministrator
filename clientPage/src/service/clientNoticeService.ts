import api from "../utils/api";

const clientNoticeService = {
  async getAllNotice() {
    const response = await api.get("client/notice");
    return response.data;
  },
};

export default clientNoticeService;
