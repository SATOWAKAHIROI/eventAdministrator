import api from "../utils/api";

const adminNoticeService = {
  async getAllNotice() {
    const response = await api.get("admin/notice");
    return response.data;
  },

  async getNoticeById(id: number) {
    const response = await api.get(`admin/notice/${id}`);

    return response.data;
  },

  async createNotice(subject: string, body: string) {
    const response = await api.post(`admin/notice`, {
      subject,
      body,
    });

    return response.data;
  },

  async editNoticeById(subject: string, body: string, id: number) {
    const response = await api.put(`admin/notice/${id}`, { subject, body });

    return response.data;
  },

  async deleteNoticeById(id: number) {
    const response = await api.delete(`admin/notice/${id}`);

    return response.data;
  },
};

export default adminNoticeService;
