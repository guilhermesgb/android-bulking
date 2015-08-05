package com.guilhermesgb.robospiceretrofit.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Table(name = "GuideItems")
public class GuideItem extends Model {

    @Column private Integer version = null;
    @Column private String name = null;
    @Column private Integer guideItemId = null;
    @Column private String category = null;
    @Column private String description = null;
    @Column private String shortDescription = null;
    @Column private String email = null;
    @Column private String phone = null;
    @Column private String website = null;
    @Column private String address = null;
    @Column private String schedule = null;
    @Column private Double minCost = null;
    @Column private Double maxCost = null;
    @Column private String cost = null;
    @Column private String creditCard = null;
    @Column private String breakfast = null;
    @Column private String capacity = null;
    @Column private String metro = null;
    @Column private String tram = null;
    @Column private String bus = null;
    @Column private String plane = null;
    @Column private String boat = null;
    @Column private String car = null;
    @Column private String gondola = null;
    @Column private String train = null;
    @Column private String bike = null;
    @Column private String walking = null;

    private final JsonObject rawBody;

    public GuideItem() {
        super();
        this.rawBody = new JsonObject();
    }

    public GuideItem(JsonObject rawBody) {
        super();
        this.rawBody = rawBody;
        setVersion();
        setName();
        setGuideItemId();
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
        GuideItem self = new Select().from(GuideItem.class)
                .where("guideItemId = ?", getGuideItemId()).executeSingle();
        if (self != null) {
            if (this.getVersion() > self.getVersion()) {
                self.delete();
                this.save();
            }
        }
        else {
            this.save();
        }
    }

    public void setVersion() {
        final JsonElement version = rawBody.get("custom_fields").getAsJsonObject().get("version");
        setFieldValue(version, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.version = version.getAsInt();
            }
        });
        if (this.version == null) {
            this.version = 1;
        }
    }

    public Integer getVersion() {
        Integer version = this.version;
        if (version == null) {
            version = 1;
        }
        return version;
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

    public void setGuideItemId() {
        final JsonElement id = rawBody.get("custom_fields").getAsJsonObject().get("id");
        setFieldValue(id, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.guideItemId = id.getAsInt();
            }
        });
    }

    public Integer getGuideItemId() {
        return this.guideItemId;
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
                GuideItem.this.minCost = minCost.getAsDouble();
            }
        });
    }

    public Double getMinCost() {
        return this.minCost;
    }

    public void setMaxCost() {
        final JsonElement maxCost = rawBody.get("custom_fields").getAsJsonObject().get("max_cost");
        setFieldValue(maxCost, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.maxCost = maxCost.getAsDouble();
            }
        });
    }

    public Double getMaxCost() {
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

    public static List<GuideItem> retrieveStoredGuideItems() {
        return new Select().all().from(GuideItem.class).execute();
    }

    public static List<GuideItem> parseGuideItems(JsonObject response) throws IOException {
        List<GuideItem> parsedResponse = new LinkedList<>();
        JsonArray posts = response.getAsJsonArray("posts");
        for (JsonElement element : posts) {
            JsonObject post = element.getAsJsonObject();
            parsedResponse.add(new GuideItem(post));
        }
        return parsedResponse;
    }

}
