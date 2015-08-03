package com.guilhermesgb.robospiceretrofit.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import retrofit.client.Response;

public class GuideItem extends ResponseModel {

    private String name = null;
    private Integer id = null;
    private String category = null;
    private String description = null;
    private String shortDescription = null;
    private String email = null;
    private String phone = null;
    private String website = null;
    private String address = null;
    private String schedule = null;
    private Integer minCost = null;
    private Integer maxCost = null;
    private String cost = null;
    private String creditCard = null;
    private String breakfast = null;
    private String capacity = null;
    private String metro = null;
    private String tram = null;
    private String bus = null;
    private String plane = null;
    private String boat = null;
    private String car = null;
    private String gondola = null;
    private String train = null;
    private String bike = null;
    private String walking = null;

    private final JsonObject rawBody;

    public static List<GuideItem> parseGuideItems(Response response) throws IOException {
        JsonObject responseBody = new JsonParser().parse(getBodyString(response)).getAsJsonObject();
        List<GuideItem> parsedResponse = new LinkedList<>();
        JsonArray posts = responseBody.getAsJsonArray("posts");
        for (JsonElement element : posts) {
            JsonObject post = element.getAsJsonObject();
            parsedResponse.add(new GuideItem(post));
        }
        return parsedResponse;
    }

    public GuideItem(Response response) throws IOException {
        this(new JsonParser().parse(getBodyString(response)).getAsJsonObject());
    }

    public GuideItem(JsonObject rawBody) {
        this.rawBody = rawBody;
        setName();
        setId();
        setCategory();
        setDescription();
        setShortDescription();
        setEmail();
        setPhone();
        setWebsite();
        setAddress();
        setSchedule();
        setMinCost();
        setMaxCost();
        setCost();
        setCreditCard();
        setBreakfast();
        setCapacity();
        setMetro();
        setTram();
        setBus();
        setPlane();
        setBoat();
        setCar();
        setGondola();
        setTrain();
        setBike();
        setWalking();
    }

    public void setName() {
        final JsonElement name = rawBody.get("title");
        setFieldValue(name, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.name = name.getAsString();
            }
        });
    }

    public String getName() {
        return this.name;
    }

    public void setId() {
        final JsonElement id = rawBody.get("custom_fields").getAsJsonObject().get("id");
        setFieldValue(id, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.id = id.getAsInt();
            }
        });
    }

    public Integer getId() {
        return this.id;
    }

    public void setCategory() {
        final JsonElement category = rawBody.get("custom_fields").getAsJsonObject().get("category");
        setFieldValue(category, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.category = category.getAsString();
            }
        });
    }

    public String getCategory() {
        return this.category;
    }

    public void setDescription() {
        final JsonElement description = rawBody.get("custom_fields")
                .getAsJsonObject().get("description");
        setFieldValue(description, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.description = description.getAsString();
            }
        });
    }

    public String getDescription() {
        return this.description;
    }

    public void setShortDescription() {
        final JsonElement shortDescription = rawBody.get("custom_fields")
                .getAsJsonObject().get("shortDescription");
        setFieldValue(shortDescription, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.shortDescription = shortDescription.getAsString();
            }
        });
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setEmail() {
        final JsonElement email = rawBody.get("custom_fields").getAsJsonObject().get("email");
        setFieldValue(email, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.email = email.getAsString();
            }
        });
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone() {
        final JsonElement phone = rawBody.get("custom_fields").getAsJsonObject().get("phone");
        setFieldValue(phone, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.phone = phone.getAsString();
            }
        });
    }

    public String getPhone() {
        return this.phone;
    }

    public void setWebsite() {
        final JsonElement website = rawBody.get("custom_fields").getAsJsonObject().get("website");
        setFieldValue(website, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.website = website.getAsString();
            }
        });
    }

    public String getWebsite() {
        return this.website;
    }

    public void setAddress() {
        final JsonElement address = rawBody.get("custom_fields").getAsJsonObject().get("address");
        setFieldValue(address, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.address = address.getAsString();
            }
        });
    }

    public String getAddress() {
        return this.address;
    }

    public void setSchedule() {
        final JsonElement schedule = rawBody.get("custom_fields").getAsJsonObject().get("schedule");
        setFieldValue(schedule, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.schedule = schedule.getAsString();
            }
        });
    }

    public String getSchedule() {
        return this.schedule;
    }

    public void setMinCost() {
        final JsonElement minCost = rawBody.get("custom_fields").getAsJsonObject().get("min_cost");
        setFieldValue(minCost, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.minCost = minCost.getAsInt();
            }
        });
    }

    public Integer getMinCost() {
        return this.minCost;
    }

    public void setMaxCost() {
        final JsonElement maxCost = rawBody.get("custom_fields").getAsJsonObject().get("max_cost");
        setFieldValue(maxCost, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.maxCost = maxCost.getAsInt();
            }
        });
    }

    public Integer getMaxCost() {
        return this.maxCost;
    }

    public void setCost() {
        final JsonElement cost = rawBody.get("custom_fields").getAsJsonObject().get("cost");
        setFieldValue(cost, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.cost = cost.getAsString();
            }
        });
    }

    public String getCost() {
        return this.cost;
    }

    public void setCreditCard() {
        final JsonElement creditCard = rawBody.get("custom_fields").getAsJsonObject().get("creditcard");
        setFieldValue(creditCard, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.creditCard = creditCard.getAsString();
            }
        });
    }

    public String getCreditCard() {
        return this.creditCard;
    }

    public void setBreakfast() {
        final JsonElement breakfast = rawBody.get("custom_fields").getAsJsonObject().get("breakfast");
        setFieldValue(breakfast, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.breakfast = breakfast.getAsString();
            }
        });
    }

    public String getBreakfast() {
        return this.breakfast;
    }

    public void setCapacity() {
        final JsonElement capacity = rawBody.get("custom_fields").getAsJsonObject().get("capacity");
        setFieldValue(capacity, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.capacity = capacity.getAsString();
            }
        });
    }

    public String getCapacity() {
        return this.capacity;
    }

    public void setMetro() {
        final JsonElement metro = rawBody.get("custom_fields").getAsJsonObject().get("metro");
        setFieldValue(metro, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.metro = metro.getAsString();
            }
        });
    }

    public String getMetro() {
        return this.metro;
    }

    public void setTram() {
        final JsonElement tram = rawBody.get("custom_fields").getAsJsonObject().get("tram");
        setFieldValue(tram, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.tram = tram.getAsString();
            }
        });
    }

    public String getTram() {
        return this.tram;
    }

    public void setBus() {
        final JsonElement bus = rawBody.get("custom_fields").getAsJsonObject().get("bus");
        setFieldValue(bus, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.bus = bus.getAsString();
            }
        });
    }

    public String getBus() {
        return this.bus;
    }

    public void setPlane() {
        final JsonElement plane = rawBody.get("custom_fields").getAsJsonObject().get("plane");
        setFieldValue(plane, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.plane = plane.getAsString();
            }
        });
    }

    public String getPlane() {
        return this.plane;
    }

    public void setBoat() {
        final JsonElement boat = rawBody.get("custom_fields").getAsJsonObject().get("boat");
        setFieldValue(boat, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.boat = boat.getAsString();
            }
        });
    }

    public String getBoat() {
        return this.boat;
    }

    public void setCar() {
        final JsonElement car = rawBody.get("custom_fields").getAsJsonObject().get("car");
        setFieldValue(car, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.car = car.getAsString();
            }
        });
    }

    public String getCar() {
        return this.car;
    }

    public void setGondola() {
        final JsonElement gondola = rawBody.get("custom_fields").getAsJsonObject().get("gondola");
        setFieldValue(gondola, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.gondola = gondola.getAsString();
            }
        });
    }

    public String getGondola() {
        return this.gondola;
    }

    public void setTrain() {
        final JsonElement train = rawBody.get("custom_fields").getAsJsonObject().get("train");
        setFieldValue(train, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.train = train.getAsString();
            }
        });
    }

    public String getTrain() {
        return this.train;
    }

    public void setBike() {
        final JsonElement bike = rawBody.get("custom_fields").getAsJsonObject().get("bike");
        setFieldValue(bike, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.bike = bike.getAsString();
            }
        });
    }

    public String getBike() {
        return this.bike;
    }

    public void setWalking() {
        final JsonElement walking = rawBody.get("custom_fields").getAsJsonObject().get("walking");
        setFieldValue(walking, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.walking = walking.getAsString();
            }
        });
    }

    public String getWalking() {
        return this.walking;
    }

    public void setFieldValue(JsonElement value, SetFieldValue callback) {
        if (value != null && !value.isJsonNull() && !value.getAsString().trim().isEmpty()) {
            callback.doSetFieldValue();
        }
    }

    protected interface SetFieldValue {
        void doSetFieldValue();
    }

}
