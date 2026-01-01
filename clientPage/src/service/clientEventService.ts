import api from "../utils/api";

const clientEventService = {
  async getAllEvent() {
    const response = await api.get("/client/event");
    return response.data;
  },

  async getEventById(id: number) {
    const response = await api.get(`client/event/${id}`);

    return response.data;
  },
};

export default clientEventService;
